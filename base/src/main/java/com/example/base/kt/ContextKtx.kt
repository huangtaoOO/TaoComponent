package com.example.base.kt

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

/**
 * Author: huangtao
 * Date: 2023/1/19
 * Desc: Context扩展
 */

fun Context.findActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) {
            return context
        }
        context = context.baseContext
    }
    return null
}