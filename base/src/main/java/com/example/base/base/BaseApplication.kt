package com.example.base.base

import android.app.Application

/**
 * Author: huangtao
 * Date: 2023/1/16
 * Desc:
 */
open class BaseApplication : Application() {

    companion object {
        lateinit var instance: BaseApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}