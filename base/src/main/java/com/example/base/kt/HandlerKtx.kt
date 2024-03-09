package com.example.base.kt

import android.os.Looper

/**
 * Author: huangtao
 * Date: 2023/1/19
 * Desc: Handler扩展
 */
fun postIdleHandle(action: () -> Unit) {
    val looper = Looper.getMainLooper()
    looper.queue.addIdleHandler {
        action.invoke()
        return@addIdleHandler false
    }

}