package com.example.lib_download.model

import com.example.lib_download.core.DownloadStatus

/**
 * Author: huangtao
 * Date: 2023/1/6
 * Desc:
 */
data class DownloadModel(
    //链接
    val url: String,
    //保存路径
    val savePath: String,
) {

    /**
     * 完成大小
     */
    @Volatile
    var completeSize: Long = 0
        internal set

    /**
     * 文件总大小
     */
    var totalSize: Long = 0
        internal set

    /**
     * 状态
     */
    var status: DownloadStatus = DownloadStatus.IDLE
        internal set
}