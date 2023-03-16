package com.example.base.navigation

import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavigationCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.example.base.RouterURL

abstract class DefaultNavigationCallback : NavigationCallback {
    override fun onFound(postcard: Postcard?) {
    }

    override fun onLost(postcard: Postcard?) {
    }

    override fun onInterrupt(postcard: Postcard?) {
        when (postcard?.tag) {
            is NotLoginException -> {
                ARouter.getInstance().build(RouterURL.LOGIN).navigation(null,this)
            }
            else -> {

            }
        }
    }
}