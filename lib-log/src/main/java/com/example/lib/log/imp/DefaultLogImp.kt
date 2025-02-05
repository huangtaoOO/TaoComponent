package com.example.lib.log.imp

import com.example.lib.log.core.LogLevel

class DefaultLogImp(private var level: Int) : LogImp {

    override fun logV(
        logInstancePtr: Long,
        tag: String?,
        filename: String?,
        funcname: String?,
        linuxTid: Int,
        pid: Int,
        tid: Long,
        maintid: Long,
        log: String?
    ) {
        if (level <= LogLevel.VERBOSE.value) {
            android.util.Log.v(tag, log ?: "")
        }
    }

    override fun logI(
        logInstancePtr: Long,
        tag: String?,
        filename: String?,
        funcname: String?,
        linuxTid: Int,
        pid: Int,
        tid: Long,
        maintid: Long,
        log: String?
    ) {
        if (level <= LogLevel.INFO.value) {
            android.util.Log.i(tag, log ?: "")
        }
    }

    override fun logD(
        logInstancePtr: Long,
        tag: String?,
        filename: String?,
        funcname: String?,
        linuxTid: Int,
        pid: Int,
        tid: Long,
        maintid: Long,
        log: String?
    ) {
        if (level <= LogLevel.DEBUG.value) {
            android.util.Log.d(tag, log ?: "")
        }
    }

    override fun logW(
        logInstancePtr: Long,
        tag: String?,
        filename: String?,
        funcname: String?,
        linuxTid: Int,
        pid: Int,
        tid: Long,
        maintid: Long,
        log: String?
    ) {
        if (level <= LogLevel.WARNING.value) {
            android.util.Log.w(tag, log ?: "")
        }
    }

    override fun logE(
        logInstancePtr: Long,
        tag: String?,
        filename: String?,
        funcname: String?,
        linuxTid: Int,
        pid: Int,
        tid: Long,
        maintid: Long,
        log: String?
    ) {
        if (level <= LogLevel.ERROR.value) {
            android.util.Log.e(tag, log ?: "")
        }
    }

    override fun logF(
        logInstancePtr: Long,
        tag: String?,
        filename: String?,
        funcname: String?,
        linuxTid: Int,
        pid: Int,
        tid: Long,
        maintid: Long,
        log: String?
    ) {
        if (level <= LogLevel.FATAL.value) {
            android.util.Log.w(tag, log ?: "")
        }
    }

    override fun getLogLevel(logInstancePtr: Long): Int {
        return level
    }

    override fun setAppenderMode(logInstancePtr: Long, mode: Int) {
    }

    override fun openLogInstance(
        level: Int,
        mode: Int,
        cacheDir: String?,
        logDir: String?,
        nameprefix: String?,
        cacheDays: Int
    ): Long {
        return 0
    }

    override fun getXlogInstance(nameprefix: String?): Long {
        return 0
    }

    override fun releaseXlogInstance(nameprefix: String?) {
    }

    override fun appenderOpen(
        level: Int,
        mode: Int,
        cacheDir: String?,
        logDir: String?,
        nameprefix: String?,
        cacheDays: Int
    ) {
    }


    override fun appenderClose() {
    }

    override fun appenderFlush(logInstancePtr: Long, isSync: Boolean) {

    }

    override fun setConsoleLogOpen(logInstancePtr: Long, isOpen: Boolean) {
    }

    override fun setMaxFileSize(logInstancePtr: Long, aliveSeconds: Long) {
    }

    override fun setMaxAliveTime(logInstancePtr: Long, aliveSeconds: Long) {
    }

}