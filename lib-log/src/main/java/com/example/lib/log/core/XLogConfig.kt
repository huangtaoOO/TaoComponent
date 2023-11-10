package com.example.lib.log.core

import android.app.Application
import com.example.lib.log.Constant

/**
 * Author: huangtao
 * Date: 2023/11/10
 * Desc: 日志配置实体类
 * @param async 是否异步打印日志
 * @param level 打印什么级别以上的日志
 * @param logPath 日志存储的位置 建议单独一个文件夹
 * @param cachePath 日志缓存的位置
 * @param namePrefix 日志文件名的前缀
 * @param cacheDay 日志缓存的天数
 * @param consolePrint 是否在控制打印
 * @param publicKey 公钥 如果需要加密
 * @param maxFileSize 日志文件最大大小
 */
data class XLogConfig(
    val context: Application,
    val level: LogLevel = LogLevel.INFO,
    val async: Boolean = true,
    val logPath: String = defaultLogPath(context),
    val cachePath: String = defaultCachePath(context),
    val namePrefix: String = Constant.TAG,
    val cacheDay: Int = 0,
    val consolePrint: Boolean = false,
    val publicKey: String = "",
    val maxFileSize: Long = Constant.MAX_FILE_SIZE
)