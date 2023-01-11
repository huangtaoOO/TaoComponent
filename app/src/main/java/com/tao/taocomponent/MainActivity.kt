package com.tao.taocomponent

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lib_download.DownloadConfig
import com.example.lib_download.ktx.createDownloadFile
import com.example.lib_download.ktx.createDownloadTask
import com.example.lib_ktx.viewbinding.binding
import com.tao.taocomponent.databinding.ActivityMainBinding

@SuppressLint("SetTextI18n")
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by binding()

    private val downloadTask by lazy {
        val url = "https://dldir1.qq.com/qqfile/qq/TIM3.4.3/TIM3.4.3.22064.exe"
        createDownloadTask(url, createDownloadFile(context = this, url)) { progress, total ->
            binding.tvProgress.text = "${progress}/${total}"
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DownloadConfig.setContext(application)

        binding.btnDownload.setOnClickListener {
            if (downloadTask.isComplete()) {
                downloadTask.resetDownloadTask()
            } else {
                downloadTask.download()
            }
        }

        binding.btnSuspend.setOnClickListener {
            downloadTask.pauseDownload()
        }
    }
}