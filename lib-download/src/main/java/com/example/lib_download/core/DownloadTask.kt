package com.example.lib_download.core

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.lib_download.DownloadConfig
import com.example.lib_download.exception.DownloadException
import com.example.lib_download.model.DownloadModel
import com.example.lib_download.model.SubDownloadModel
import java.io.File
import java.io.RandomAccessFile
import java.util.concurrent.Executor

/**
 * Author: huangtao
 * Date: 2022/12/27
 * Desc:
 */
class DownloadTask(
    private val download: DownloadModel,
    //回调监听
    private val listener: DownloadListener
) : DownloadListener {

    /**
     * 线程数
     */
    private var threadNum = DownloadConfig.threadNum

    /**
     * 子任务列表
     */
    private val subTasks = mutableListOf<SubDownloadTask>()

    /**
     * 线程池
     */
    private val mExecutorService: Executor by lazy {
        DownloadConfig.mExecutorService
    }

    private val mHandle = Handler(Looper.getMainLooper())

    /**
     * 开始下载
     * 如果是暂停的则从上次的位置继续下载
     */
    @Throws(DownloadException::class)
    fun download() {
        mExecutorService.execute {
            download.apply {
                if (status == DownloadStatus.DOWNLOADING)
                    return@execute
                status = DownloadStatus.DOWNLOADING

                val list = DownloadConfig.dbHelper.queryByTaskTag(url, savePath)
                subTasks.clear()
                totalSize = 0
                completeSize = 0
                for (model in list) {
                    val subTask = SubDownloadTask(model, this@DownloadTask)
                    totalSize += model.taskSize
                    completeSize += model.completeSize
                    subTasks.add(subTask)
                }
                if (subTasks.isEmpty()) {
                    downloadNewTask()
                } else if (subTasks.size == threadNum) {
                    continueDownloadTask()
                } else {
                    resetDownloadTask()
                }
            }
        }
    }

    /**
     * 暂停下载任务
     */
    fun pauseDownload() {
        download.apply {
            if (status != DownloadStatus.DOWNLOADING) {
                return
            }
            for (task in subTasks) {
                task.pause()
            }
            status = DownloadStatus.PAUSE
            listener.onPause()
        }
    }

    /**
     *重置下载任务
     */
    @Throws(DownloadException::class)
    fun resetDownloadTask() {
        mExecutorService.execute {
            download.status = DownloadStatus.IDLE
            for (task in subTasks) {
                DownloadConfig.dbHelper.delete(task.subDownload)
                task.subDownload.status = DownloadStatus.ERROR
            }
            subTasks.clear()
            downloadNewTask()
        }
    }

    private fun continueDownloadTask() {
        startAsyncDownload()
    }

    @Throws(DownloadException::class)
    private fun downloadNewTask() {
        mExecutorService.execute {
            listener.onStart()
            download.status = DownloadStatus.DOWNLOADING
            download.completeSize = 0
            val targetFile = File(download.savePath)
            val destinationFolder = File(targetFile.parent ?: "")
            if (!destinationFolder.exists()) {
                destinationFolder.mkdirs()
            }
            targetFile.createNewFile()

            val size = DownloadConfig.httpHelper.obtainTotalSize(download.url)
            if (size <= 0){
                throw DownloadException()
            }
            if (size <= DownloadConfig.downloadThreshold) {
                threadNum = 1
            }
            download.totalSize = size
            val averageSize = size / threadNum
            for (i in 0 until threadNum) {
                var taskSize = averageSize
                if (i == (threadNum - 1)) {
                    taskSize += download.totalSize % threadNum
                }
                var start = 0L
                var index = i
                while (index > 0) {
                    start += subTasks[i - 1].subDownload.taskSize
                    index--
                }
                // FIX: 下载总长度比源文件小于1的情况
                val offset = if (i == (threadNum - 1)) {
                    0
                } else {
                    1
                }
                val end = start + taskSize - offset
                val subModel = SubDownloadModel(
                    download.url, taskSize, start,
                    end, start, targetFile.absolutePath
                )
                val subTask =
                    SubDownloadTask(subModel, this)
                subTasks.add(subTask)
                DownloadConfig.dbHelper.insert(subTask.subDownload)
            }

            val file = RandomAccessFile(targetFile.absolutePath, "rwd")
            file.setLength(size)
            file.close()

            startAsyncDownload()
        }
    }

    private fun startAsyncDownload() {
        download.status = DownloadStatus.DOWNLOADING
        for (task in subTasks) {
            if (task.subDownload.completeSize < task.subDownload.taskSize) {
                mExecutorService.execute(task)
            }
        }
    }

    /**
     * 下载进度
     */
    override fun onDownloading(progress: Long, total: Long) {
        synchronized(this) {
            mHandle.post {
                download.completeSize += progress
                listener.onDownloading(download.completeSize, download.totalSize)
            }
        }
    }

    /**
     * 子任务完成回调
     * 此方法被将被调用threadNum次
     */
    override fun onComplete() {
        for (task in subTasks) {
            if (task.subDownload.status != DownloadStatus.COMPLETED) {
                return
            }
        }
        Log.i(
            DownloadConfig.TAG,
            "${DownloadConfig.TAG}{${hashCode()}},下载完成 当前的线程名：${Thread.currentThread().name} "
        )
        for (task in subTasks) {
            DownloadConfig.dbHelper.delete(task.subDownload)
        }
        download.status = DownloadStatus.COMPLETED
    }


    /**
     * 子任务出现异常的回调
     */
    override fun onError(msg: String) {
        //出现异常 暂停,清除任务重新下载
        pauseDownload()
        for (task in subTasks) {
            DownloadConfig.dbHelper.delete(task.subDownload)
        }
        subTasks.clear()
        listener.onError(msg)
        listener.onCancel()
    }

    /**
     * 任务是否完成
     */
    fun isComplete(): Boolean {
        return download.status == DownloadStatus.COMPLETED
    }
}