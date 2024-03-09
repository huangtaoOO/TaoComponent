package com.example.base.data

import com.example.base.entity.BaseEntity
import com.example.base.network.HttpException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Author: huangtao
 * Date: 2023/1/19
 * Desc: 数据源
 */
interface DataSource {

    /**
     * 封装网络请求，捕获请求过程中出现的异常
     * @param action 请求体
     * @return 请求结果
     */
    suspend fun <R : BaseEntity<T>, T> request(action: suspend () -> R): Result<BaseEntity<T>> =
        withContext(Dispatchers.IO) {
            try {
                val response: R = action.invoke()
                errorConversion(response)
            } catch (e: Exception) {
                Result.failure(HttpException(error = e))
            }
        }

    private fun <T> errorConversion(response: BaseEntity<T>): Result<BaseEntity<T>> =
        if (response.isSuccess) {
            Result.success(response)
        } else {
            Result.failure(Throwable(response.errorMsg))
        }
}