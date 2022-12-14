## [多线程下载](https://blog.csdn.net/tao_789456/article/details/128457971)

使用方法:

```kotlin
//初始化，必须
DownloadConfig
    //设置上下文 必须
    .setContext(application)
    //设置线程数 默认4 非必选
    .setThreadNum(2)
    //设置线程池 默认公用协程线程池 非必选
    .setExecutor(Dispatchers.Default.asExecutor())
    //设置下载实现 默认HttpURLConnection实现 非必选
    .setHttpHelper(object : DownloadHttpHelper{
        //...
    })
    //设置序列化实现 默认sqlite实现 非必须
    .setDbHelper(object : DownloadDbHelper{
        //...
    })

//构建下载任务
val url = "https://dldir1.qq.com/qqfile/qq/TIM3.4.3/TIM3.4.3.22064.exe"
val downloadTask = createDownloadTask(url, createDownloadFile(context = this, url)) { progress, total ->
    binding.tvProgress.text = "${progress}/${total}"
}

//下载任务
downloadTask.download()

//重新下载
downloadTask.resetDownloadTask()

//暂停下载
downloadTask.pauseDownload()

//判断任务是否完成
downloadTask.isComplete()
```

具体参考：app module 或者文章链接

- 使用过程中如有BUG，请提issue
- 使用过程中如有疑问或者更好的想法，欢迎进群讨论[Android 学习交流群](https://jq.qq.com/?_wv=1027&k=QmvEoGKM)