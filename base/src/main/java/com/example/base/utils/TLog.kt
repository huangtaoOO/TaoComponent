package com.example.base.utils

import android.util.Log

object TLog {

    @JvmStatic
    fun i(tag: String, msg: String) {
        Log.i(tag, msg)
    }

    @JvmStatic
    fun d(tag: String, msg: String) {
        Log.d(tag, msg)
    }

    @JvmStatic
    fun e(tag: String, msg: String) {
        Log.e(tag, msg)
    }

    @JvmStatic
    fun w(tag: String, msg: String) {
        Log.w(tag, msg)
    }

    @JvmStatic
    fun v(tag: String, msg: String) {
        Log.v(tag, msg)
    }

    @JvmStatic
    fun e(tag: String, msg: String, tr: Throwable?) {
        Log.e(tag, msg, tr)
    }

    @JvmStatic
    fun longInfo(tag: String, str: String) {
        if (str.length > 4000) {
            Log.i(tag, str.substring(0, 4000))
            longInfo(tag, str.substring(4000))
        } else
            Log.i(tag, str)
    }
}