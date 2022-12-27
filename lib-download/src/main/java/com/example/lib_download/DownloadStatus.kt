package com.example.lib_download

/**
 * Author: huangtao
 * Date: 2022/12/27
 * Desc: 下载状态枚举
 */
enum class DownloadStatus {

    /**
     * 空闲，默认状态
     */
    IDLE,

    /**
     * 完成
     */
    COMPLETED,

    /**
     * 下载中
     */
    DOWNLOADING,

    /**
     * 暂停
     */
    PAUSE,

    /**
     * 出错
     */
    ERROR
}