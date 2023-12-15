# Android多模块项目

## [多线程下载](https://blog.csdn.net/tao_789456/article/details/128457971)

[![](https://jitpack.io/v/huangtaoOO/TaoComponent.svg)](https://jitpack.io/#huangtaoOO/TaoComponent)

使用方法:

com.github.huangtaoOO.TaoComponent:lib-download:0.0.11

```gradle
//引入依赖 gradle 7.0以下 项目根目录 build.gradle 文件
allprojects {
repositories {
...
maven { url 'https://jitpack.io' }
}
}
//引入依赖 gradle 7.0以上 项目根目录 setting.gradle 文件
dependencyResolutionManagement {
...
repositories {
...
maven { url 'https://jitpack.io' }
}
}
//模块module build.gradle
dependencies {
...
implementation 'com.github.huangtaoOO.TaoComponent:lib-download:0.0.11'
}
```

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

# Android 日志库
[![](https://jitpack.io/v/huangtaoOO/TaoComponent.svg)](https://jitpack.io/#huangtaoOO/TaoComponent)

使用方法:

com.github.huangtaoOO.TaoComponent:lib-log:0.0.11

```kotlin
/**
 *  初始化，必须
 *  async 是否异步打印日志
 *  level 打印什么级别以上的日志
 *  logPath 日志存储的位置 建议单独一个文件夹
 *  cachePath 日志缓存的位置
 *  namePrefix 日志文件名的前缀
 *  cacheDay 日志缓存的天数
 *  consolePrint 是否在控制打印
 *  publicKey 公钥 如果需要加密
 *  maxFileSize 日志文件最大大小
 */
TLogClient.init(XLogConfig(getApplication()))

//打印日志
Log.i("TLog", "test log")

//关闭日志
TLogClient.close()

//刷新日志
TLogClient.flush()
```
相关实现文章

https://blog.csdn.net/tao_789456/article/details/117638566
https://blog.csdn.net/tao_789456/article/details/118113526

日志解码工具

1.使用腾讯的工具,Mars源码log/crypt下的解密文件进行解析。 https://github.com/Tencent/mars

2.使用开源方案：https://github.com/zhanlan123/YXlogDecode


# Android性能数据监控
施工中...


具体参考：app module 或者文章链接

- 使用过程中如有BUG，请提issue
- 使用过程中如有疑问或者更好的想法，欢迎进群讨论[Android 学习交流群](https://jq.qq.com/?_wv=1027&k=QmvEoGKM)
- 