package com.example.lib_download

import androidx.annotation.WorkerThread
import java.io.InputStream

/**
 * Author: huangtao
 * Date: 2022/12/27
 * Desc: 下载的网络请求接口定义
 */
interface DownloadHttpHelper {

    /**
     * 获取需要下载链接的文件长度
     * @param url 链接
     */
    @WorkerThread
    fun obtainTotalSize(url: String): Long

    /**
     * 获取文件分段内的内容
     * @param url 链接
     * @param start 开始位置
     * @param end 结束位置
     * @return 输入流
     */
    @WorkerThread
    fun obtainStreamByRange(url: String, start: Long, end: Long): InputStream?
}