package com.tao.taocomponent

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.lib_download.DownloadConfig
import com.example.lib_download.DownloadListener
import com.example.lib_download.DownloadStatus
import com.example.lib_download.DownloadTask
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var tvProgress :TextView
    private lateinit var btnDownload : Button
    private lateinit var btnSuspend :Button

    private val downloadTask by lazy {
        val file = filesDir.absolutePath + File.separator + "download" + File.separator + "tim.exe"
        DownloadTask(
            "https://dldir1.qq.com/qqfile/qq/TIM3.4.3/TIM3.4.3.22064.exe",
            file,
            object : DownloadListener {
                @SuppressLint("SetTextI18n")
                override fun onDownloading(progress: Long, total: Long) {
                    runOnUiThread {
                        tvProgress.text = "下载进度：${progress}/${total}"
                    }
                }
            })
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
            if (downloadTask.status == DownloadStatus.COMPLETED){
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