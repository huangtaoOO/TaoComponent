package com.example.lib.log

import android.os.Build
import android.os.Looper
import android.os.Process
import android.util.Log
import com.example.lib.log.core.LogLevel
import com.example.lib.log.imp.DefaultLogImp
import com.example.lib.log.imp.LogImp
import com.tencent.mars.xlog.Xlog

/**
 * Author: huangtao
 * Date: 2023/11/10
 * Desc: 日志
 */
object Log {

    private const val TAG = "TLog"

    private val debugLog: LogImp = DefaultLogImp(LogLevel.NONE.value)

    private var logImp: LogImp = debugLog

    private val mLogInstanceMap = mutableMapOf<String, LogInstance>()

    private val SYS_INFO by lazy {
        val sb = StringBuilder()
        try {
            sb.append("VERSION.RELEASE:[").append(Build.VERSION.RELEASE)
            sb.append("] \nVERSION.CODENAME:[").append(Build.VERSION.CODENAME)
            sb.append("] \nVERSION.INCREMENTAL:[").append(Build.VERSION.INCREMENTAL)
            sb.append("] \nBOARD:[").append(Build.BOARD)
            sb.append("] \nDEVICE:[").append(Build.DEVICE)
            sb.append("] \nDISPLAY:[").append(Build.DISPLAY)
            sb.append("] \nFINGERPRINT:[").append(Build.FINGERPRINT)
            sb.append("] \nHOST:[").append(Build.HOST)
            sb.append("] \nMANUFACTURER:[").append(Build.MANUFACTURER)
            sb.append("] \nMODEL:[").append(Build.MODEL)
            sb.append("] \nPRODUCT:[").append(Build.PRODUCT)
            sb.append("] \nTAGS:[").append(Build.TAGS)
            sb.append("] \nTYPE:[").append(Build.TYPE)
            sb.append("] \nUSER:[").append(Build.USER).append("]")
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        sb.toString()
    }

    @JvmStatic
    fun openLogInstance(
        level: Int,
        mode: Int,
        cacheDir: String,
        logDir: String,
        nameprefix: String,
        cacheDays: Int
    ): LogInstance? {
        synchronized(mLogInstanceMap) {
            if (mLogInstanceMap.containsKey(nameprefix)) {
                return mLogInstanceMap[nameprefix]
            }
            val instance = LogInstance(
                level,
                mode,
                cacheDir,
                logDir,
                cacheDays,
                nameprefix,
            )
            mLogInstanceMap[nameprefix] = instance
            return instance
        }
    }


    @JvmStatic
    fun closeLogInstance(prefix: String) {
        synchronized(mLogInstanceMap) {
            if (mLogInstanceMap.containsKey(prefix)) {
                mLogInstanceMap.remove(prefix)
                logImp.releaseXlogInstance(prefix)
            }
        }
    }

    @JvmStatic
    fun getLogInstance(prefix: String?): LogInstance? {
        synchronized(mLogInstanceMap) {
            if (mLogInstanceMap.containsKey(prefix)) {
                return mLogInstanceMap[prefix]
            }
            return null
        }
    }

    @JvmStatic
    fun setLogImp(imp: LogImp) {
        logImp = imp
    }

    @JvmStatic
    fun appenderOpen(
        level: Int,
        mode: Int,
        cacheDir: String,
        logDir: String,
        nameprefix: String,
        cacheDays: Int
    ) {
        logImp.appenderOpen(level, mode, cacheDir, logDir, nameprefix, cacheDays)
    }

    @JvmStatic
    fun appenderClose() {
        logImp.appenderClose()
        for (entry in mLogInstanceMap.entries) {
            closeLogInstance(entry.key)
        }
    }

    @JvmStatic
    fun appenderCloseFlush() {
        logImp.appenderFlush(0, false)
        for (entry in mLogInstanceMap.entries) {
            entry.value.appenderFlush()
        }
    }

    @JvmStatic
    fun appenderFlushSync(isSync: Boolean) {
        logImp.appenderFlush(0, isSync)
        for (entry in mLogInstanceMap.entries) {
            entry.value.appenderFlushSync(isSync)
        }
    }

    @JvmStatic
    fun getLogLevel(): Int {
        return logImp.getLogLevel(0)
    }

    @JvmStatic
    @Deprecated("废弃，启动的时候设置level")
    fun setLevel(level: Int, jni: Boolean) {
        Log.w(TAG, "new log level: $level")
        if (jni) {
            Log.e(TAG, "no jni log level support")
        }
    }

    fun setConsoleLogOpen(isOpen: Boolean) {
        logImp.setConsoleLogOpen(0, isOpen)
        for (entry in mLogInstanceMap.entries) {
            entry.value.setConsoleLogOpen(isOpen)
        }
    }

    @JvmStatic
    fun getImpl(): LogImp {
        return logImp
    }


    @JvmStatic
    fun f(tag: String, msg: String) {
        logImp.logF(
            0,
            tag,
            "",
            "",
            0,
            Process.myPid(),
            Thread.currentThread().id,
            Looper.getMainLooper().thread.id,
            msg
        )
    }

    @JvmStatic
    fun e(tag: String, msg: String) {
        logImp.logE(
            0,
            tag,
            "",
            "",
            0,
            Process.myPid(),
            Thread.currentThread().id,
            Looper.getMainLooper().thread.id,
            msg
        )
    }

    @JvmStatic
    fun w(tag: String, msg: String) {
        logImp.logW(
            0,
            tag,
            "",
            "",
            0,
            Process.myPid(),
            Thread.currentThread().id,
            Looper.getMainLooper().thread.id,
            msg
        )
    }

    @JvmStatic
    fun i(tag: String, msg: String) {
        logImp.logI(
            0,
            tag,
            "",
            "",
            0,
            Process.myPid(),
            Thread.currentThread().id,
            Looper.getMainLooper().thread.id,
            msg
        )
    }

    @JvmStatic
    fun d(tag: String, msg: String) {
        logImp.logD(
            0,
            tag,
            "",
            "",
            0,
            Process.myPid(),
            Thread.currentThread().id,
            Looper.getMainLooper().thread.id,
            msg
        )
    }

    @JvmStatic
    fun v(tag: String, msg: String) {
        logImp.logV(
            0,
            tag,
            "",
            "",
            0,
            Process.myPid(),
            Thread.currentThread().id,
            Looper.getMainLooper().thread.id,
            msg
        )
    }


    @JvmStatic
    fun printErrStackTrace(tag: String, tr: Throwable?, msg: String) {
        var log = msg
        log += "\n" + Log.getStackTraceString(tr)
        logImp.logE(
            0,
            tag,
            "",
            "",
            0,
            Process.myPid(),
            Thread.currentThread().id,
            Looper.getMainLooper().thread.id,
            msg
        )
    }

    @JvmStatic
    fun getSysInfo(): String {
        return SYS_INFO
    }


    class LogInstance(
        level: Int,
        mode: Int,
        cacheDir: String,
        logDir: String,
        cacheDays: Int,
        mPrefix: String,
    ) {

        val mLogInstancePtr: Long =
            logImp.openLogInstance(level, mode, cacheDir, logDir, mPrefix, cacheDays)

        fun f(tag: String, format: String, vararg obj: Any?) {
            if (getLogLevel() <= LogLevel.ERROR.value && mLogInstancePtr != 0L) {
                val log = String.format(format, *obj)
                logImp.logF(
                    mLogInstancePtr,
                    tag,
                    "",
                    "",
                    Process.myTid(),
                    Process.myPid(),
                    Thread.currentThread().id,
                    Looper.getMainLooper().thread.id,
                    log
                )

            }
        }

        fun e(tag: String, format: String, vararg obj: Any?) {
            if (getLogLevel() <= LogLevel.ERROR.value && mLogInstancePtr != 0L) {
                val log = String.format(format, *obj).ifEmpty { "" }
                logImp.logE(
                    mLogInstancePtr,
                    tag,
                    "",
                    "",
                    Process.myTid(),
                    Process.myPid(),
                    Thread.currentThread().id,
                    Looper.getMainLooper().thread.id,
                    log
                )

            }
        }

        fun w(tag: String, format: String, vararg obj: Any?) {
            if (getLogLevel() <= LogLevel.ERROR.value && mLogInstancePtr != 0L) {
                val log = String.format(format, *obj).ifEmpty { "" }
                logImp.logW(
                    mLogInstancePtr,
                    tag,
                    "",
                    "",
                    Process.myTid(),
                    Process.myPid(),
                    Thread.currentThread().id,
                    Looper.getMainLooper().thread.id,
                    log
                )

            }
        }

        fun i(tag: String, format: String, vararg obj: Any?) {
            if (getLogLevel() <= LogLevel.ERROR.value && mLogInstancePtr != 0L) {
                val log = String.format(format, *obj).ifEmpty { "" }
                logImp.logI(
                    mLogInstancePtr,
                    tag,
                    "",
                    "",
                    Process.myTid(),
                    Process.myPid(),
                    Thread.currentThread().id,
                    Looper.getMainLooper().thread.id,
                    log
                )

            }
        }

        fun d(tag: String, format: String, vararg obj: Any?) {
            if (getLogLevel() <= LogLevel.ERROR.value && mLogInstancePtr != 0L) {
                val log = String.format(format, *obj).ifEmpty { "" }
                logImp.logD(
                    mLogInstancePtr,
                    tag,
                    "",
                    "",
                    Process.myTid(),
                    Process.myPid(),
                    Thread.currentThread().id,
                    Looper.getMainLooper().thread.id,
                    log
                )
            }
        }

        fun v(tag: String, format: String, vararg obj: Any?) {
            if (getLogLevel() <= LogLevel.ERROR.value && mLogInstancePtr != 0L) {
                val log = String.format(format, *obj).ifEmpty { "" }
                logImp.logV(
                    mLogInstancePtr,
                    tag,
                    "",
                    "",
                    Process.myTid(),
                    Process.myPid(),
                    Thread.currentThread().id,
                    Looper.getMainLooper().thread.id,
                    log
                )
            }
        }

        fun printErrStackTrace(tag: String, tr: Throwable, format: String, vararg obj: Any?) {
            if (getLogLevel() <= LogLevel.ERROR.value && mLogInstancePtr != 0L) {
                var log = String.format(format, *obj).ifEmpty { "" }
                log += " " + Log.getStackTraceString(tr)
                logImp.logE(
                    mLogInstancePtr,
                    tag,
                    "",
                    "",
                    Process.myTid(),
                    Process.myPid(),
                    Thread.currentThread().id,
                    Looper.getMainLooper().thread.id,
                    log
                )
            }
        }

        fun appenderFlush() {
            logImp.appenderFlush(mLogInstancePtr, false)
        }

        fun appenderFlushSync(isSync: Boolean) {
            logImp.appenderFlush(mLogInstancePtr, isSync)
        }

        fun getLogLevel(): Int {
            return logImp.getLogLevel(mLogInstancePtr)
        }

        fun setConsoleLogOpen(isOpen: Boolean) {
            logImp.setConsoleLogOpen(mLogInstancePtr, isOpen = isOpen)
        }

    }
}