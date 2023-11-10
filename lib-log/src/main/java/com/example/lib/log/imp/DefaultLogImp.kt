package com.example.lib.log.imp

import com.example.lib.log.core.LogLevel

class DefaultLogImp(private var level: Int) : LogImp {
    override fun logV(
        tag: String,
        filename: String,
        funcName: String,
        line: Int,
        pid: Int,
        tid: Long,
        mainTid: Long,
        log: String
    ) {
        if (level <= LogLevel.VERBOSE.value) {
            android.util.Log.v(tag, log)
        }
    }

    override fun logI(
        tag: String,
        filename: String,
        funcName: String,
        line: Int,
        pid: Int,
        tid: Long,
        mainTid: Long,
        log: String
    ) {
        if (level <= LogLevel.INFO.value) {
            android.util.Log.i(tag, log)
        }
    }

    override fun logD(
        tag: String,
        filename: String,
        funcName: String,
        line: Int,
        pid: Int,
        tid: Long,
        mainTid: Long,
        log: String
    ) {
        if (level <= LogLevel.DEBUG.value) {
            android.util.Log.d(tag, log)
        }
    }

    override fun logW(
        tag: String,
        filename: String,
        funcName: String,
        line: Int,
        pid: Int,
        tid: Long,
        mainTid: Long,
        log: String
    ) {
        if (level <= LogLevel.WARNING.value) {
            android.util.Log.w(tag, log)
        }
    }

    override fun logE(
        tag: String,
        filename: String,
        funcName: String,
        line: Int,
        pid: Int,
        tid: Long,
        mainTid: Long,
        log: String
    ) {
        if (level <= LogLevel.ERROR.value) {
            android.util.Log.w(tag, log)
        }
    }

    override fun logF(
        tag: String,
        filename: String,
        funcName: String,
        line: Int,
        pid: Int,
        tid: Long,
        mainTid: Long,
        log: String
    ) {
        if (level <= LogLevel.FATAL.value) {
            android.util.Log.w(tag, log)
        }
    }

    override fun getLogLevel(): Int {
        return level
    }

    override fun setLogLevel(level: LogLevel) {
        this.level = level.value
    }


    override fun appenderClose() {
    }

    override fun appenderFlush(isSync: Boolean) {
    }
}