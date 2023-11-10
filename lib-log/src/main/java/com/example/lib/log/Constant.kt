package com.example.lib.log

/**
 * Author: huangtao
 * Date: 2023/11/10
 * Desc: 常量池
 * TODO: 1.监听退出事件 三个方向 A.registerActivityLifecycleCallbacks中onDestroyed的事件 B.监听Linux的SIG信号 C.
 * TODO: 2.监听崩溃事件
 * TODO: 3.提供压缩日志的方法，关闭后重启
 * TODO: 4.根据PLog添加相关网络和activity的监听
 */
object Constant {

    const val TAG = "XLog"

    const val DEFAULT_LOG_FILE_NAME = "TLog"

    const val DEFAULT_CACHE_FILE_NAME = "TLog-Cache"

    const val MAX_FILE_SIZE = 1024 * 1024 * 2L
}