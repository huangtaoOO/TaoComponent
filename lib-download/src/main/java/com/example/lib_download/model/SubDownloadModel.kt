package com.example.lib_download.model

import com.example.lib_download.core.DownloadStatus

data class SubDownloadModel(
    //下载路径
    val url: String,
    //子任务的大小
    val taskSize: Long,
    //开始位置
    val startPos: Long,
    //结束位置
    val endPos: Long,
    //当前位置
    var currentPos: Long = startPos,
    //保存的文件路径
    val saveFile: String
){
    /**
     * 当前任务的状态
     */
    @Volatile
    var status: DownloadStatus = DownloadStatus.IDLE

    /**
     * 已经下载的大小
     */
    @Volatile
    var completeSize = 0L
}
