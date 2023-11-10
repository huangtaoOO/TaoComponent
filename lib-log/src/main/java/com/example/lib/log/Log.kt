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
            sb.append("] VERSION.CODENAME:[").append(Build.VERSION.CODENAME)
            sb.append("] VERSION.INCREMENTAL:[").append(Build.VERSION.INCREMENTAL)
            sb.append("] BOARD:[").append(Build.BOARD)
            sb.append("] DEVICE:[").append(Build.DEVICE)
            sb.append("] DISPLAY:[").append(Build.DISPLAY)
            sb.append("] FINGERPRINT:[").append(Build.FINGERPRINT)
            sb.append("] HOST:[").append(Build.HOST)
            sb.append("] MANUFACTURER:[").append(Build.MANUFACTURER)
            sb.append("] MODEL:[").append(Build.MODEL)
            sb.append("] PRODUCT:[").append(Build.PRODUCT)
            sb.append("] TAGS:[").append(Build.TAGS)
            sb.append("] TYPE:[").append(Build.TYPE)
            sb.append("] USER:[").append(Build.USER).append("]")
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