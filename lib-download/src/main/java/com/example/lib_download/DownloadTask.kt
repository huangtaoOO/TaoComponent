package com.example.lib_download

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.lib_download.core.DownloadListener
import com.example.lib_download.core.DownloadStatus
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
    private val threadNum = DownloadConfig.threadNum

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
    fun download() {
        mExecutorService.execute {
            if (download.status == DownloadStatus.DOWNLOADING) {
                return@execute
            }
            download.status = DownloadStatus.DOWNLOADING

            val list = DownloadConfig.dbHelper.queryByTaskTag(download.url, download.savePath)
            subTasks.clear()
            download.totalSize = 0
            download.completeSize = 0
            for (model in list) {
                val subTask = SubDownloadTask(model, this)
                download.totalSize += model.taskSize
                download.completeSize += model.completeSize
                subTasks.add(subTask)
            }

            if (subTasks.isEmpty()) {
                downloadNewTask()
            } else if (subTasks.size == threadNum) {
                existDownloadTask()
            } else {
                resetDownloadTask()
            }
        }
    }

    /**
     * 暂停下载任务
     */
    fun pauseDownload() {
        if (download.status != DownloadStatus.DOWNLOADING) {
            return
        }
        for (task in subTasks) {
            task.pause()
        }
        download.status = DownloadStatus.PAUSE
        listener.onPause()
    }

    /**
     *重置下载任务
     */
    fun resetDownloadTask() {
        mExecutorService.execute {
            for (task in subTasks) {
                DownloadConfig.dbHelper.delete(task.subDownload)
            }
            subTasks.clear()
            downloadNewTask()
        }
    }

    private fun existDownloadTask() {
        startAsyncDownload()
    }

    private fun downloadNewTask() {
        mExecutorService.execute {
            listener.onStart()
            download.completeSize = 0
            val targetFile = File(download.savePath)
            val destinationFolder = File(targetFile.parent ?: "")
            if (!destinationFolder.exists()) {
                destinationFolder.mkdirs()
            }
            targetFile.createNewFile()

            val size = DownloadConfig.httpHelper.obtainTotalSize(download.url)
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
                val end = start + taskSize - 1
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