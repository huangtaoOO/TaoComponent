
## [多线程下载](https://blog.csdn.net/tao_789456/article/details/128457971)

使用方法:
```kotlin
//初始化，必须
DownloadConfig.setContext(application)

//构建下载任务
val downloadTask = DownloadTask(
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

//下载任务
downloadTask.download()

//重新下载
downloadTask.resetDownloadTask()

//暂停下载
downloadTask.pauseDownload()
```
具体参考：app module 或者文章链接



- 使用过程中如有BUG，请提issue
- 使用过程中如有疑问或者更好的想法，欢迎进群讨论[Android 学习交流群](https://jq.qq.com/?_wv=1027&k=QmvEoGKM)