package com.example.lib.log.utils

import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

object LogZipTools {

    /**
     * 压缩文件夹或文件
     * @param src 源文件路径
     * @param zip 目标文件路径
     */
    @Throws(Exception::class)
    fun zip(src: String, zip: String) {
        val outZip = ZipOutputStream(FileOutputStream(zip))
        val file = File(src)
        zipFiles(file.parent?.plus(File.separator) ?: "", file.name, outZip)
        outZip.finish()
        outZip.close()
    }

    @Throws(Exception::class)
    private fun zipFiles(folder: String, fileStr: String, zip: ZipOutputStream) {
        val file = File(folder + fileStr)
        if (file.isFile) {
            val input = file.inputStream()
            val zipEntry = ZipEntry(fileStr)
            zip.putNextEntry(zipEntry)
            var len: Int
            val buffer = ByteArray(4096)
            while (input.read(buffer).also { len = it } != -1) {
                zip.write(buffer, 0, len)
            }
            zip.closeEntry()
        } else {
            val fileList = file.listFiles() ?: return
            if (fileList.isNotEmpty()) {
                val zipEntry = ZipEntry(fileStr + File.separator)
                zip.putNextEntry(zipEntry)
                zip.closeEntry()
                for (f in fileList) {
                    zipFiles(folder,fileStr + File.separator + f.name, zip)
                }
            }
        }
    }
}