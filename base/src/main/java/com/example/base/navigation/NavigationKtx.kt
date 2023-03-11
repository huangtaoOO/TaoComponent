package com.example.base.navigation

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.launcher.ARouter

fun Context.navigation(path: String, onArrival: (() -> Unit)) {
    ARouter.getInstance().build(path).navigation(this, object :
        DefaultNavigationCallback() {

        override fun onArrival(postcard: Postcard?) {
            onArrival.invoke()
        }

    })
}