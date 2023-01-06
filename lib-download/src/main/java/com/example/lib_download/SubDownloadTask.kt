package com.example.lib_download

import android.util.Log
import com.example.lib_download.core.DownloadListener
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.RandomAccessFile

/**
 * Author: huangtao
 * Date: 2022/12/27
 * Desc: 子任务下载类
 */
class SubDownloadTask(
    //下载路径
    val url: String,
    //子任务的大小
    val taskSize: Long,
    //开始位置
    val startPos: Long,
    //结束位置
    val endPos: Long,
    //当前位置
    var currentPos: Long = startPos,
    //保存的文件
    val saveFile: File,
    //回调监听
    var listener: DownloadListener? = null
) : Runnable {

    companion object {
        const val BUFFER_SIZE: Long = 1024 * 1024
    }

    /**
     * 当前任务的状态
     */
    @Volatile
    var status: DownloadStatus = DownloadStatus.IDLE

    /**
     * 已经下载的大小
     */
    @Volatile
    var completeSize = 0L

    /**
     * 暂停任务
     */
    fun pause() {
        status = DownloadStatus.PAUSE
    }

    override fun run() {
        try {
            status = DownloadStatus.DOWNLOADING
            listener?.onStart()
            val input = DownloadConfig.httpHelper.obtainStreamByRange(url, currentPos, endPos)
                ?: throw java.lang.NullPointerException("obtainStreamByRange InputStream is null")
            writeFile(input)
        } catch (e: Exception) {
            Log.e(DownloadConfig.TAG, e.message ?: "", e)
            status = DownloadStatus.ERROR
            listener?.onError(e.message ?: "")
        }
    }

    @Throws(IOException::class)
    private fun writeFile(input: InputStream) {
        Log.i(
            DownloadConfig.TAG,
            "${DownloadConfig.TAG}{${hashCode()}},写入开始 线程名：${Thread.currentThread().name} " +
                    "文件路径：${saveFile.absolutePath}"
        )
        val file = RandomAccessFile(saveFile, "rwd")
        val bufferSize = BUFFER_SIZE.coerceAtMost(taskSize).toInt()
        val buffer = ByteArray(bufferSize)
        file.seek(currentPos)
        while (true) {
            if (status != DownloadStatus.DOWNLOADING) {
                break
            }
            val offset = input.read(buffer, 0, bufferSize)
            if (offset == -1) {
                break
            }
            file.write(buffer, 0, offset)
            currentPos += offset
            completeSize += offset
            listener?.onDownloading(offset.toLong(), taskSize)
        }
        //更新状态
        if (status == DownloadStatus.DOWNLOADING) {
            status = DownloadStatus.COMPLETED
        }
        DownloadConfig.dbHelper.update(this)
        //处理回调
        if (status == DownloadStatus.COMPLETED) {
            listener?.onComplete()
        } else if (status == DownloadStatus.PAUSE) {
            listener?.onPause()
        }
        //关闭资源
        file.close()
        input.close()

        Log.i(
            DownloadConfig.TAG, "${DownloadConfig.TAG}{${hashCode()}},\n 写入状态:${status.name} " +
                    "总大小=${taskSize} 开始位置${startPos} " +
                    "结束位置${endPos} 完成大小${completeSize}"
        )
    }
}