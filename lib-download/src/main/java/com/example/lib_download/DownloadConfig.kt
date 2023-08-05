package com.example.lib_download

import android.app.Application
import com.example.lib_download.core.DownloadDbHelper
import com.example.lib_download.core.DownloadHttpHelper
import com.example.lib_download.impl.DownloadDbImpl
import com.example.lib_download.impl.DownloadHttpImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import java.util.concurrent.Executor

/**
 * Author: huangtao
 * Date: 2022/12/27
 * Desc: 下载配置接口
 */
object DownloadConfig {

    const val TAG = "DownloadManager"

    /**
     * 上下文
     */
    lateinit var context: Application
        private set

    /**
     * db实现
     */
    var dbHelper: DownloadDbHelper
        private set

    /**
     * http下载实现
     */
    var httpHelper: DownloadHttpHelper
        private set

    /**
     * 线程数
     */
    var threadNum: Int
        private set

    /**
     * 下载阈值
     * 如果下载所需的文件大小小于当前值则单线程下载
     * 默认：512 Kb
     */
    var downloadThreshold: Long
        private set

    /**
     * 线程池
     */
    var mExecutorService: Executor
        private set

    /**
     * 下载过程中出现异常是否删除已下载的内容
     * eg：下载过程中，如果出现异常，为true清除下载任务
     * 如果设置为false建议在下载完成后业务方面进行文件MD5校验，判断文件是否受损
     */
    var clearTaskInError : Boolean = true
        private set


    init {
        threadNum = 4
        downloadThreshold = 1024 * 512
        dbHelper = DownloadDbImpl()
        httpHelper = DownloadHttpImpl()
        mExecutorService = Dispatchers.IO.asExecutor()
    }

    /**
     * 必须要设置
     * 设置上下文
     */
    fun setContext(app: Application): DownloadConfig {
        context = app
        return this
    }

    /**
     * 设置自定义的DownloadDbHelper
     * 默认使用sqlite
     */
    fun setDbHelper(impl: DownloadDbHelper): DownloadConfig {
        dbHelper = impl
        return this
    }

    /**
     * 设置自定义的DownloadHttpHelper
     * 默认使用HttpURLConnection
     */
    fun setHttpHelper(impl: DownloadHttpHelper): DownloadConfig {
        httpHelper = impl
        return this
    }

    /**
     * 设置线程数
     * 默认 4
     */
    fun setThreadNum(num: Int): DownloadConfig {
        threadNum = num
        return this
    }

    /**
     * 设置线程池
     * 默认采用 协程IO线程池
     */
    fun setExecutor(executor: Executor): DownloadConfig {
        mExecutorService = executor
        return this
    }

    /**
     * 设置下载的阈值
     */
    fun setDownloadThreshold(size: Long): DownloadConfig {
        downloadThreshold = size
        return this
    }

    /**
     * 下载过程中出现异常是否删除已下载的内容
     * eg：断网情况下，如果
     */
    fun setClearTaskInError(boolean: Boolean):DownloadConfig{
        clearTaskInError = boolean
        return this
    }
}