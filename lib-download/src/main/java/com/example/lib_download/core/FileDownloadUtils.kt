package com.example.lib_download.core

import android.util.Log
import com.example.lib_download.DownloadConfig
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

/**
 * 小文件下载器
 * 单线程下载
 */
object FileDownloadUtils {

    private const val BUFFER_SIZE = 1024 * 1024

    @JvmStatic
    fun download(url: String, saveFile: File) {
        var conn: HttpURLConnection? = null
        var input: InputStream? = null
        var fos: FileOutputStream? = null
        try {
            Log.w(DownloadConfig.TAG, "小文件下载 开始下载文件 ：下载路径 = $url 目标文件 = ${saveFile.absolutePath}")
            val mUrl = URL(url)
            conn = mUrl.openConnection() as HttpURLConnection
            conn.setRequestProperty("User-Agent", "PacificHttpClient")
            conn.connectTimeout = 10000
            conn.readTimeout = 20000
            if (conn.responseCode != 200) {
                throw IOException("HTTP ${conn.responseCode} FAILED")
            }
            //已经下载的长度
            var downloadLength = 0

            var readLength: Int
            val bytes = ByteArray(BUFFER_SIZE)

            input = conn.inputStream
            fos = FileOutputStream(saveFile, false)

            while (input.read(bytes).also {
                    readLength = it
                } != -1) {
                downloadLength += readLength
                fos.write(bytes, 0, readLength)
            }

            fos.flush()
            Log.w(DownloadConfig.TAG, "成功下载文件 ：下载路径 = $url 目标文件 = ${saveFile.absolutePath}")
        } catch (e: Exception) {
            Log.e(DownloadConfig.TAG, "文件下载异常 ： ${e.message}", e)
        } finally {
            conn?.disconnect()
            input?.close()
            fos?.close()
        }
    }

}