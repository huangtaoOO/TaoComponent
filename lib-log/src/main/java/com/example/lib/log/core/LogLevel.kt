package com.example.lib.log.core

/**
 * Author: huangtao
 * Date: 2023/11/10
 * Desc: 日志级别
 */
enum class LogLevel(val value: Int) {
    ALL(0),
    VERBOSE(0),
    DEBUG(1),
    INFO(2),
    WARNING(3),
    ERROR(4),
    FATAL(5),
    NONE(6)
}