package com.example.lib_download.ktx

import android.content.Context
import com.example.lib_download.core.DownloadListener
import com.example.lib_download.DownloadTask
import com.example.lib_download.model.DownloadModel
import java.io.File

/**
 * 提供一个创建下载任务的方法
 * @param url 下载链接
 * @param savePath 保存路径
 * @param call 下载监听,不要做耗时操作
 */
fun createDownloadTask(
    url: String,
    savePath: String,
    call: ((progress: Long, total: Long) -> Unit)?
): DownloadTask {
    val model = DownloadModel(url, savePath)
    return DownloadTask(
        model,
        object : DownloadListener {
            override fun onDownloading(progress: Long, total: Long) {
                call?.invoke(progress, total)
            }
        })
}

/**
 * 根据下载的链接，截取最后一个【/】后面的内容作为文件名，
 * 文件保存默认保存在根目录的【Download-T】文件夹下，
 * 注：未对文件名称做重名处理，如果重名会出现覆盖，请自行处理此部分逻辑
 * @param context 上下文
 * @param url 下载链接
 * @return 保存的文件
 */
fun createDownloadFile(context: Context, url: String): String {
    val path = "${context.filesDir.absolutePath}${File.separator}Download-T"
    return getSavedFile(url, path).absolutePath
}

/**
 * 递归删除目录下的所有文件及子目录下所有文件
 * @param dir 将要删除的文件目录
 * @return true：删除完毕 false：递归的删除过程出现了失败
 */
fun deleteDir(dir: File): Boolean {
    if (dir.isDirectory) {
        val children = dir.list() ?: return false
        for (i in children.indices) {
            val success = deleteDir(File(dir, children[i]))
            if (!success) {
                return false
            }
        }
    }
    return dir.delete()
}

/**
 * 根据下载的链接，截取最后一个【/】后面的内容作为文件名
 * @param url 下载链接
 * @param saveCatalogue 需要保存的目录
 * @return 保存的文件
 */
private fun getSavedFile(url: String, saveCatalogue: String): File {
    val catalogue = File(saveCatalogue)
    if (!catalogue.exists()) {
        catalogue.mkdirs()
    }
    val fileName = url.substring(url.lastIndexOf("/"), url.length)
    val savePath = catalogue.absolutePath + File.separator + fileName
    val saveFile = File(savePath)
    if (saveFile.exists()) {
        saveFile.createNewFile()
    }
    return saveFile
}

