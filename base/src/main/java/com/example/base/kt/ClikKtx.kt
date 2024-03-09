package com.example.base.kt

import android.view.View

/**
 * Author: huangtao
 * Date: 2023/1/19
 * Desc: 点击事件扩展 防止重复点击
 */

fun View.click(action: (v: View?) -> Unit) {

    setOnClickListener(object : View.OnClickListener {
        private var lastClickTime = 0L

        override fun onClick(v: View?) {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime > 500) {
                lastClickTime = currentTime
                action(v)
            }
        }
    })
}
