package com.example.lib.log.core

import android.content.Context
import com.example.lib.log.Constant

/**
 * Author: huangtao
 * Date: 2023/11/10
 * Desc: 日志相关的工具函数
 */

/**
 * 获取默认的日志存储路径
 * @param app ApplicationContext
 * @return 路径
 */
fun defaultLogPath(app : Context): String =
    app.getDir(Constant.DEFAULT_LOG_FILE_NAME, Context.MODE_PRIVATE).absolutePath

/**
 * 获取默认的日志缓存路径
 * @param app ApplicationContext
 * @return 路径
 */
fun defaultCachePath(app: Context): String =
    app.getDir(Constant.DEFAULT_CACHE_FILE_NAME,Context.MODE_PRIVATE).absolutePath