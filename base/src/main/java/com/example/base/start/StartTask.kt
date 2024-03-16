package com.example.base.start

import android.app.Application

/**
 * Author: huangtao
 * Date: 2023/1/16
 * Desc: 启动任务接口，用于启动各个不同的模块 比如：初始化网络请求、初始化数据库、初始化推送等
 */
interface StartTask {

    fun start(app: Application)
}