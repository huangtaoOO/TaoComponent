package com.example.lib_download.core

import com.example.lib_download.SubDownloadTask

/**
 * Author: huangtao
 * Date: 2022/12/27
 * Desc: 下载的db存储接口定义
 */
interface DownloadDbHelper {

    /**
     * 删除一个任务
     * @param task 下载子任务
     */
    fun delete(task : SubDownloadTask)

    /**
     * 添加一个子任务
     * @param task 子任务
     */
    fun insert(task : SubDownloadTask)

    /**
     * 更新一个任务
     * @param task 子任务
     */
    fun update(task : SubDownloadTask)

    /**
     * 根据url查询相关任务
     * @param url 下载链接
     * @return 相关子任务 无：返回空列表
     */
    fun queryByTaskTag(url : String):List<SubDownloadTask>
}