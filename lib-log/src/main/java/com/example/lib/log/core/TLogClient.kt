package com.example.lib.log.core

import com.example.lib.log.Constant
import com.example.lib.log.Log
import com.example.lib.log.lifecycle.AppLifecycleCallback
import com.example.lib.log.utils.LogZipTools
import com.tencent.mars.xlog.Xlog
import java.security.interfaces.XECKey

/**
 * Author: huangtao
 * Date: 2023/11/10
 * Desc: 日志客户端 提供一系列对外接口
 */
object TLogClient {

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
        Log.setLogImp(Xlog().apply {
            setMaxFileSize(0, config.maxFileSize)
        })
        val isASync = if (config.async) {
            AppenderMode.Async.value
        } else {
            AppenderMode.Sync.value
        }
        Xlog.open(
            true,
            config.level.value,
            isASync,
            config.cachePath,
            config.logPath,
            config.namePrefix,
            config.publicKey,
            config.cacheDay
        )
        Log.setConsoleLogOpen(config.consolePrint)
        if (!isInitialized) {
            curConfig.context.registerActivityLifecycleCallbacks(AppLifecycleCallback())
        }
        Log.i(Constant.TAG, " -- TLogClient initTLog -- \n${Log.getSysInfo()}")
    }

    fun closeTLog() {
        if (this::curConfig.isInitialized) {
            Log.appenderClose()
        }
    }

    fun flushTLog(isSync: Boolean) {
        if (this::curConfig.isInitialized) {
            Log.appenderFlushSync(isSync)
        }
    }

    /**
     * 压缩日志。耗时任务请放在子线程中执行
     */
    fun zipLog(log: String, zip: String): Boolean {
        return runCatching {
            LogZipTools.zip(log, zip)
            true
        }.onFailure {
            Log.printErrStackTrace(Constant.TAG, it, "zipLog error: ${it.message}")
        }.getOrNull() ?: false
    }
}