package com.example.lib.log.lifecycle

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.example.lib.log.Constant
import com.example.lib.log.Log
import com.example.lib.log.core.TLogClient

/**
 * Author: huangtao
 * Date: 2023/11/10
 * Desc: 监听activity的生命周期
 */
class AppLifecycleCallback : Application.ActivityLifecycleCallbacks {

    private var createdCounter = 0

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        Log.i(
            Constant.TAG,
            "onActivityCreated => ${activity::class.simpleName} code=${activity.hashCode()} savedInstanceState=${savedInstanceState}"
        )
        createdCounter++
    }

    override fun onActivityStarted(activity: Activity) {
        Log.i(
            Constant.TAG,
            "onActivityStarted => ${activity::class.simpleName} code=${activity.hashCode()}"
        )
    }

    override fun onActivityResumed(activity: Activity) {
        Log.i(
            Constant.TAG,
            "onActivityResumed => ${activity::class.simpleName} code=${activity.hashCode()}"
        )
    }

    override fun onActivityPaused(activity: Activity) {
        Log.i(
            Constant.TAG,
            "onActivityPaused => ${activity::class.simpleName} code=${activity.hashCode()}"
        )
    }

    override fun onActivityStopped(activity: Activity) {
        Log.i(
            Constant.TAG,
            "onActivityStopped => ${activity::class.simpleName} code=${activity.hashCode()}"
        )
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        Log.i(
            Constant.TAG,
            "onActivitySaveInstanceState => ${activity::class.simpleName} code=${activity.hashCode()} outState=${outState}"
        )
    }

    override fun onActivityDestroyed(activity: Activity) {
        Log.i(
            Constant.TAG,
            "onActivityDestroyed => ${activity::class.simpleName} code=${activity.hashCode()}"
        )
        createdCounter--
        if (createdCounter == 0 && !activity.isChangingConfigurations) {
            Log.i(Constant.TAG, " -- APP exit -- ")
            TLogClient.flushTLog(false)
        }
    }
}