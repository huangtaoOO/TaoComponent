package com.example.base.base

import android.app.Application
import com.example.base.start.LogTask
import com.example.base.start.RouterTask
import com.example.base.start.StartTask

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
        initStartTask()
    }

    private fun initStartTask() {
        val startTasks = buildStartTask()
        startTasks.forEach {
            it.start(this)
        }
    }

    open fun buildStartTask(): MutableList<StartTask> {
        return mutableListOf(
            LogTask(),
            RouterTask()
        )
    }
}