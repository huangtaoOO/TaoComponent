package com.example.base.start

import android.app.Application
import com.example.lib.log.core.TLogClient
import com.example.lib.log.core.XLogConfig

class LogTask : StartTask {
    override fun start(app: Application) {
        TLogClient.initTLog(XLogConfig(app, consolePrint = true))
    }
}