package com.example.base.data

/**
 * Author: huangtao
 * Date: 2023/1/19
 * Desc: 用于协程内容的封装类
 * Error用来处理程序异常，不处理业务错误
 */
sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}

val Result<*>.succeeded
    get() = this is Result.Success && data != null