package com.tao.bus.user.data

import com.example.base.data.DataSource
import com.example.base.entity.BaseEntity
import com.example.base.entity.user.UserEntity
import com.example.base.exception.NotImplementedException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Author: huangtao
 * Date: 2023/1/19
 * Desc: 登录的数据源接口
 */
interface UserDataSource : DataSource {

    /**
     * 登录
     */
    suspend fun signIn(username: String, password: String): Result<BaseEntity<UserEntity>> =
        Result.failure(NotImplementedException())

    /**
     * 登出
     */
    suspend fun signOut(): Result<BaseEntity<Unit>> =
        Result.failure(NotImplementedException())


    /**
     * 注册
     */
    suspend fun register(username: String, password: String): Result<BaseEntity<Unit>> =
        Result.failure(NotImplementedException())


    /**
     * 保存用户信息
     */
    suspend fun saveUserInfo(entity: UserEntity) {}

    /**
     * 清除当前用户信息
     */
    suspend fun clearUserInfo() {}

    /**
     * 获取用户信息
     */
    suspend fun obtainUserInfo(): Result<UserEntity> =
        Result.failure(NotImplementedException())

    /**
     * 获取用户信息流
     */
    fun obtainUserInfoFlow(): StateFlow<UserEntity?> = MutableStateFlow(null)
}
