package com.example.base.navigation

import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavigationCallback

abstract class DefaultNavigationCallback : NavigationCallback {
    override fun onFound(postcard: Postcard?) {
    }

    override fun onLost(postcard: Postcard?) {
    }


    override fun onInterrupt(postcard: Postcard?) {
    }
}