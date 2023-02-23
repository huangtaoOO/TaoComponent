package com.example.lib_download.impl

import android.os.Build
import android.util.Log
import com.example.lib_download.DownloadConfig
import com.example.lib_download.core.DownloadHttpHelper
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

/**
 * Author: huangtao
 * Date: 2022/12/27
 * Desc: 下载的http 默认实现
 */
class DownloadHttpImpl : DownloadHttpHelper {
    override fun obtainTotalSize(url: String): Long {
        val urlPath = URL(url)
        val connection = urlPath.openConnection() as HttpURLConnection
        connection.setRequestProperty("Accept-Encoding","identity")
        val length = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connection.contentLengthLong
        } else {
            connection.contentLength.toLong()
        }
        Log.i(
            DownloadConfig.TAG, "${DownloadConfig.TAG},请求路径：${url}\n总长度：${length}"
        )
        return length
    }

    override fun obtainStreamByRange(url: String, start: Long, end: Long): InputStream? {
        Log.i(
            DownloadConfig.TAG,
            "${DownloadConfig.TAG},请求路径：${url}\n开始位置：${start} 结束位置${end}"
        )
        val urlPath = URL(url)
        val connection = urlPath.openConnection() as HttpURLConnection
        connection.setRequestProperty("Accept-Encoding","identity")
        connection.addRequestProperty("RANGE", "bytes=${start}-${end}")
        connection.connectTimeout = 3000
        connection.connect()
        val lengthLong = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connection.contentLengthLong
        } else {
            connection.contentLength.toLong()
        }
        Log.i(
            DownloadConfig.TAG,
            "${DownloadConfig.TAG},请求路径：${url}\n " +
                    "状态码：${connection.responseCode} 长度：${lengthLong}"
        )
        return if (connection.responseCode == 206) {
            connection.inputStream
        } else {
            null
        }
    }
}