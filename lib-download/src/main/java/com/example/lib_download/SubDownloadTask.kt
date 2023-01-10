package com.example.lib_download

import android.util.Log
import com.example.lib_download.core.DownloadListener
import com.example.lib_download.core.DownloadStatus
import com.example.lib_download.model.SubDownloadModel
import java.io.IOException
import java.io.InputStream
import java.io.RandomAccessFile

/**
 * Author: huangtao
 * Date: 2022/12/27
 * Desc: 子任务下载类
 */
class SubDownloadTask(
    internal val subDownload: SubDownloadModel,
    //回调监听
    var listener: DownloadListener? = null
) : Runnable {

    companion object {
        const val BUFFER_SIZE: Long = 1024 * 1024
    }

    /**
     * 暂停任务
     */
    fun pause() {
        subDownload.status = DownloadStatus.PAUSE
    }

    override fun run() {
        try {
            subDownload.status = DownloadStatus.DOWNLOADING
            listener?.onStart()
            val input = DownloadConfig.httpHelper.obtainStreamByRange(
                subDownload.url,
                subDownload.currentPos,
                subDownload.endPos
            )
                ?: throw java.lang.NullPointerException("obtainStreamByRange InputStream is null")
            writeFile(input)
        } catch (e: Exception) {
            Log.e(DownloadConfig.TAG, e.message ?: "", e)
            subDownload.status = DownloadStatus.ERROR
            listener?.onError(e.message ?: "")
        }
    }

    @Throws(IOException::class)
    private fun writeFile(input: InputStream) {
        Log.i(
            DownloadConfig.TAG,
            "${DownloadConfig.TAG}{${hashCode()}},写入开始 线程名：${Thread.currentThread().name} " +
                    "文件路径：${subDownload.saveFile}"
        )
        val file = RandomAccessFile(subDownload.saveFile, "rwd")
        val bufferSize = BUFFER_SIZE.coerceAtMost(subDownload.taskSize).toInt()
        val buffer = ByteArray(bufferSize)
        file.seek(subDownload.currentPos)
        while (true) {
            if (subDownload.status != DownloadStatus.DOWNLOADING) {
                break
            }
            val offset = input.read(buffer, 0, bufferSize)
            if (offset == -1) {
                break
            }
            file.write(buffer, 0, offset)
            subDownload.currentPos += offset
            subDownload.completeSize += offset
            listener?.onDownloading(offset.toLong(), subDownload.taskSize)
        }
        //更新状态
        if (subDownload.status == DownloadStatus.DOWNLOADING) {
            subDownload.status = DownloadStatus.COMPLETED
        }
        DownloadConfig.dbHelper.update(subDownload)
        //处理回调
        if (subDownload.status == DownloadStatus.COMPLETED) {
            listener?.onComplete()
        } else if (subDownload.status == DownloadStatus.PAUSE) {
            listener?.onPause()
        }
        //关闭资源
        file.close()
        input.close()

        Log.i(
            DownloadConfig.TAG,
            "${DownloadConfig.TAG}{${hashCode()}},\n 写入状态:${subDownload.status.name} " +
                    "总大小=${subDownload.taskSize} 开始位置${subDownload.startPos} " +
                    "结束位置${subDownload.endPos} 完成大小${subDownload.completeSize}"
        )
    }
}