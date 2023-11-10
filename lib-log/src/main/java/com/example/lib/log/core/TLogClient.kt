package com.example.lib.log.core

import com.example.lib.log.Constant
import com.example.lib.log.lifecycle.AppLifecycleCallback
import com.tencent.mars.xlog.Log
import com.tencent.mars.xlog.Xlog

/**
 * Author: huangtao
 * Date: 2023/11/10
 * Desc: 日志客户端 提供一系列对外接口
 */
object TLogClient {

    init {
        System.loadLibrary("c++_shared")
        System.loadLibrary("marsxlog")
    }

    private lateinit var curConfig: XLogConfig

    /**
     * Application中的onCreate进行调用初始化
     */
    fun initTLog(config: XLogConfig) {
        val isInitialized = this::curConfig.isInitialized
        if (isInitialized) {
            closeTLog()
        }
        curConfig = config
        Log.setLogImp(Xlog())
        val isASync = if (config.async) {
            Xlog.AppednerModeAsync
        } else {
            Xlog.AppednerModeSync
        }
        Xlog.setConsoleLogOpen(config.consolePrint)
        Xlog.setMaxFileSize(config.maxFileSize)
        Xlog.appenderOpen(
            config.level.value,
            isASync,
            config.cachePath,
            config.logPath,
            config.namePrefix,
            config.cacheDay,
            config.publicKey
        )
        if (!isInitialized) {
            curConfig.context.registerActivityLifecycleCallbacks(AppLifecycleCallback())
        }
        Runtime.getRuntime().addShutdownHook(Thread {
            Log.i(Constant.TAG, "-- Runtime.getRuntime().addShutdownHook --")
        })
    }

    fun closeTLog() {
        if (this::curConfig.isInitialized) {
            Log.appenderClose()
        }
    }

    fun flushTLog(isSync: Boolean) {
        if (this::curConfig.isInitialized) {
            Log.appenderFlush(isSync)
        }
    }

    fun getLogPath(): String? {
        return if (this::curConfig.isInitialized) {
            curConfig.logPath
        } else {
            null
        }
    }
}