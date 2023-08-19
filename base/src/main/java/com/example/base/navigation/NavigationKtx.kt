package com.example.base.navigation

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.launcher.ARouter

/**
 * 基础导航
 * @param path              路径
 * @param needLogin         是否需要登录
 * @param onArrival         导航回调
 */
fun Context.navigation(
    path: String,
    needLogin: Boolean = true,
    onArrival: (() -> Unit)
) {
    val builder = ARouter.getInstance().build(path)
    if (!needLogin) {
        builder.greenChannel()
    }
    builder.navigation(this, object :
        DefaultNavigationCallback() {

        override fun onArrival(postcard: Postcard?) {
            onArrival.invoke()
        }

    })
}