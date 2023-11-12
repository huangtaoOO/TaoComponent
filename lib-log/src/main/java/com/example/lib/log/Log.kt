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
    fun setLogImp(imp: LogImp) {
        logImp = imp
    }

    @JvmStatic
    fun getImpl(): LogImp {
        return logImp
    }

    @JvmStatic
    fun appenderClose() {
        logImp.appenderClose()
    }

    @JvmStatic
    fun appenderFlush(isSync: Boolean) {
        logImp.appenderFlush(isSync)
    }

    @JvmStatic
    fun getLogLevel(): Int {
        return logImp.getLogLevel()
    }

    @JvmStatic
    fun setLevel(level: LogLevel, jni: Boolean) {
        logImp.setLogLevel(level)
        w(TAG, "new log level: $level")

        if (jni) {
            Xlog.setLogLevel(level.value)
        }
    }

    @JvmStatic
    fun f(tag: String, msg: String) {
        f(tag, msg, null)
    }

    @JvmStatic
    fun e(tag: String, msg: String) {
        e(tag, msg, null)
    }

    @JvmStatic
    fun w(tag: String, msg: String) {
        w(tag, msg, null)
    }

    @JvmStatic
    fun i(tag: String, msg: String) {
        i(tag, msg, null)
    }

    @JvmStatic
    fun d(tag: String, msg: String) {
        d(tag, msg, null)
    }

    @JvmStatic
    fun v(tag: String, msg: String) {
        v(tag, msg, null)
    }

    @JvmStatic
    fun f(tag: String, format: String, vararg obj: Any?) {
        val log = String.format(format, obj)
        logImp.logF(
            tag,
            "",
            "",
            0,
            Process.myPid(),
            Process.myTid().toLong(),
            Looper.getMainLooper().thread.id,
            log
        )
    }

    @JvmStatic
    fun e(tag: String, format: String, vararg obj: Any?) {
        val log = String.format(format, obj)
        logImp.logE(
            tag,
            "",
            "",
            0,
            Process.myPid(),
            Process.myTid().toLong(),
            Looper.getMainLooper().thread.id,
            log
        )
    }

    @JvmStatic
    fun w(tag: String, format: String, vararg obj: Any?) {
        val log = String.format(format, obj)
        logImp.logW(
            tag,
            "",
            "",
            0,
            Process.myPid(),
            Process.myTid().toLong(),
            Looper.getMainLooper().thread.id,
            log
        )
    }

    @JvmStatic
    fun i(tag: String, format: String, vararg obj: Any?) {
        val log = String.format(format, obj)
        logImp.logI(
            tag,
            "",
            "",
            0,
            Process.myPid(),
            Process.myTid().toLong(),
            Looper.getMainLooper().thread.id,
            log
        )
    }

    @JvmStatic
    fun d(tag: String, format: String, vararg obj: Any?) {
        val log = String.format(format, obj)
        logImp.logD(
            tag,
            "",
            "",
            0,
            Process.myPid(),
            Process.myTid().toLong(),
            Looper.getMainLooper().thread.id,
            log
        )
    }


    @JvmStatic
    fun v(tag: String, format: String, vararg obj: Any?) {
        val log = String.format(format, obj)
        logImp.logV(
            tag,
            "",
            "",
            0,
            Process.myPid(),
            Process.myTid().toLong(),
            Looper.getMainLooper().thread.id,
            log
        )
    }

    @JvmStatic
    fun printErrStackTrace(tag: String, tr: Throwable?, format: String, vararg obj: Any?) {
        var log = String.format(format, obj)
        log += "  " + Log.getStackTraceString(tr)
        logImp.logE(
            tag,
            "",
            "",
            0,
            Process.myPid(),
            Process.myTid().toLong(),
            Looper.getMainLooper().thread.id,
            log
        )
    }

    @JvmStatic
    fun getSysInfo(): String {
        return SYS_INFO
    }
}