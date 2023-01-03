package com.tao.taocomponent

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.lib_download.DownloadConfig
import com.example.lib_download.ktx.createDownloadTask
import com.example.lib_download.ktx.isComplete
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var tvProgress :TextView
    private lateinit var btnDownload : Button
    private lateinit var btnSuspend :Button

    private val downloadTask by lazy {
        val file = filesDir.absolutePath + File.separator + "download" + File.separator + "tim.exe"
        createDownloadTask("https://dldir1.qq.com/qqfile/qq/TIM3.4.3/TIM3.4.3.22064.exe",file)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DownloadConfig.setContext(application)

        tvProgress = findViewById(R.id.tv_progress)
        btnDownload = findViewById(R.id.btn_download)
        btnSuspend = findViewById(R.id.btn_suspend)

        btnDownload.setOnClickListener {
            if (downloadTask.isComplete()){
                downloadTask.resetDownloadTask()
            }else{
                downloadTask.download()
            }
        }

        btnSuspend.setOnClickListener {
            downloadTask.pauseDownload()
        }
    }
}