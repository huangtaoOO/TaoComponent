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
     * 线程池
     */
    var mExecutorService: Executor
        private set


    init {
        threadNum = 4
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
}