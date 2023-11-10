package com.example.lib.log.imp

import com.example.lib.log.core.LogLevel

/**
 * Author: huangtao
 * Date: 2023/11/10
 * Desc: 日志接口
 */
interface LogImp {

    /**
     * logV
     * @param tag 标签
     * @param filename 文件名
     * @param funcName 函数名
     * @param line 行
     * @param pid 进程id
     * @param tid 线程id
     * @param mainTid 主线程id
     * @param log 日志
     */
    fun logV(
        tag: String,
        filename: String,
        funcName: String,
        line: Int,
        pid: Int,
        tid: Long,
        mainTid: Long,
        log: String
    )

    /**
     * logI
     * @param tag 标签
     * @param filename 文件名
     * @param funcName 函数名
     * @param line 行
     * @param pid 进程id
     * @param tid 线程id
     * @param mainTid 主线程id
     * @param log 日志
     */
    fun logI(
        tag: String,
        filename: String,
        funcName: String,
        line: Int,
        pid: Int,
        tid: Long,
        mainTid: Long,
        log: String
    )

    /**
     * logD
     * @param tag 标签
     * @param filename 文件名
     * @param funcName 函数名
     * @param line 行
     * @param pid 进程id
     * @param tid 线程id
     * @param mainTid 主线程id
     * @param log 日志
     */
    fun logD(
        tag: String,
        filename: String,
        funcName: String,
        line: Int,
        pid: Int,
        tid: Long,
        mainTid: Long,
        log: String
    )

    /**
     * logW
     * @param tag 标签
     * @param filename 文件名
     * @param funcName 函数名
     * @param line 行
     * @param pid 进程id
     * @param tid 线程id
     * @param mainTid 主线程id
     * @param log 日志
     */
    fun logW(
        tag: String,
        filename: String,
        funcName: String,
        line: Int,
        pid: Int,
        tid: Long,
        mainTid: Long,
        log: String
    )

    /**
     * logE
     * @param tag 标签
     * @param filename 文件名
     * @param funcName 函数名
     * @param line 行
     * @param pid 进程id
     * @param tid 线程id
     * @param mainTid 主线程id
     * @param log 日志
     */
    fun logE(
        tag: String,
        filename: String,
        funcName: String,
        line: Int,
        pid: Int,
        tid: Long,
        mainTid: Long,
        log: String
    )

    /**
     * logF
     * @param tag 标签
     * @param filename 文件名
     * @param funcName 函数名
     * @param line 行
     * @param pid 进程id
     * @param tid 线程id
     * @param mainTid 主线程id
     * @param log 日志
     */
    fun logF(
        tag: String,
        filename: String,
        funcName: String,
        line: Int,
        pid: Int,
        tid: Long,
        mainTid: Long,
        log: String
    )

    /**
     * 设置日志级别
     * @param level 级别
     */
    fun setLogLevel(level: LogLevel)

    /**
     * 获取当前日志级别
     * @return 级别
     */
    fun getLogLevel(): Int

    /**
     * 关闭日志
     */
    fun appenderClose()

    /**
     * 刷新写出日志
     */
    fun appenderFlush(isSync: Boolean)
}