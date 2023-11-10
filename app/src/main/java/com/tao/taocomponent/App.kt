package com.tao.taocomponent

import com.alibaba.android.arouter.launcher.ARouter
import com.example.base.base.BaseApplication
import com.example.lib.log.core.TLogClient
import com.example.lib.log.core.XLogConfig
import dagger.hilt.android.HiltAndroidApp

/**
 * Author: huangtao
 * Date: 2023/1/16
 * Desc:
 */
@HiltAndroidApp
class App : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        TLogClient.initTLog(XLogConfig(this, consolePrint = true))

        ARouter.openLog()      // 打印日志
        ARouter.openDebug()    // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init(this)  // 尽可能早，推荐在Application中初始化
    }
}