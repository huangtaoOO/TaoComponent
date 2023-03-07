package com.example.lib_download.core

/**
 * Author: huangtao
 * Date: 2022/12/27
 * Desc: 下载状态枚举
 */
enum class DownloadStatus {

    /**
     * 空闲，默认状态
     * 下载任务创建，任务重制的时候设置为IDLE
     */
    IDLE,

    /**
     * 完成
     * 所有子任务下载完成设置为COMPLETED
     */
    COMPLETED,

    /**
     * 下载中
     * 调用download或downloadNewTask方法后即为DOWNLOADING
     */
    DOWNLOADING,

    /**
     * 暂停
     * 调用pauseDownload方法后
     */
    PAUSE,

    /**
     * 出错
     */
    ERROR
}