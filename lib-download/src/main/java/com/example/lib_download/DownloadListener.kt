package com.example.lib_download

/**
 * Author: huangtao
 * Date: 2022/12/27
 * Desc: 下载的事件监听
 */
interface DownloadListener {

    /**
     * 开始下载
     */
    fun onStart() {}

    /**
     * 下载中
     * @param progress 进度 字节
     * @param total 总数 字节
     */
    fun onDownloading(progress: Long, total: Long) {}

    /**
     * 暂停
     */
    fun onPause() {}

    /**
     * 取消下载
     */
    fun onCancel() {}

    /**
     * 下载完成
     */
    fun onComplete() {}

    /**
     * 出错
     * @param msg 错误信息
     */
    fun onError(msg: String) {}
}