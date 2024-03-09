package com.example.base.network

class HttpException(
    val code: Int = -1,
    val msg: String = "网络异常，请稍后重试",
    val error: Throwable? = null
) : Exception()