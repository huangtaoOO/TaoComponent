package com.example.lib.log.imp

/**
 * Author: huangtao
 * Date: 2023/11/10
 * Desc: 日志接口
 */
interface LogImp {

    fun logV(
        logInstancePtr: Long,
        tag: String?,
        filename: String?,
        funcname: String?,
        linuxTid: Int,
        pid: Int,
        tid: Long,
        maintid: Long,
        log: String?
    )

    fun logI(
        logInstancePtr: Long,
        tag: String?,
        filename: String?,
        funcname: String?,
        linuxTid: Int,
        pid: Int,
        tid: Long,
        maintid: Long,
        log: String?
    )

    fun logD(
        logInstancePtr: Long,
        tag: String?,
        filename: String?,
        funcname: String?,
        linuxTid: Int,
        pid: Int,
        tid: Long,
        maintid: Long,
        log: String?
    )

    fun logW(
        logInstancePtr: Long,
        tag: String?,
        filename: String?,
        funcname: String?,
        linuxTid: Int,
        pid: Int,
        tid: Long,
        maintid: Long,
        log: String?
    )

    fun logE(
        logInstancePtr: Long,
        tag: String?,
        filename: String?,
        funcname: String?,
        linuxTid: Int,
        pid: Int,
        tid: Long,
        maintid: Long,
        log: String?
    )

    fun logF(
        logInstancePtr: Long,
        tag: String?,
        filename: String?,
        funcname: String?,
        linuxTid: Int,
        pid: Int,
        tid: Long,
        maintid: Long,
        log: String?
    )

    fun getLogLevel(logInstancePtr: Long): Int

    fun setAppenderMode(logInstancePtr: Long, mode: Int)

    fun openLogInstance(
        level: Int,
        mode: Int,
        cacheDir: String?,
        logDir: String?,
        nameprefix: String?,
        cacheDays: Int
    ): Long

    fun getXlogInstance(nameprefix: String?): Long

    fun releaseXlogInstance(nameprefix: String?)

    fun appenderOpen(
        level: Int,
        mode: Int,
        cacheDir: String?,
        logDir: String?,
        nameprefix: String?,
        cacheDays: Int
    )

    fun appenderClose()

    fun appenderFlush(logInstancePtr: Long, isSync: Boolean)

    fun setConsoleLogOpen(logInstancePtr: Long, isOpen: Boolean)

    fun setMaxFileSize(logInstancePtr: Long, aliveSeconds: Long)

    fun setMaxAliveTime(logInstancePtr: Long, aliveSeconds: Long)

}