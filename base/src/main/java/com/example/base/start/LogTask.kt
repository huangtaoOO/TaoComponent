package com.example.base.start

import android.app.Application
import android.content.Context
import com.example.lib.log.core.TLogClient
import com.example.lib.log.core.XLogConfig

class LogTask : StartTask {
    override fun start(app: Application) {
        val config = XLogConfig(
            context = app,
            consolePrint = true
        )
        TLogClient.initTLog(config)
    }
}