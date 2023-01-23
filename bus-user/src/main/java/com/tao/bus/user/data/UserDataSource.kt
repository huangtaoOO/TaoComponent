package com.tao.bus.user.data

import com.example.base.data.DataSource
import com.example.base.entity.BaseEntity
import com.example.base.data.Result
import com.example.base.entity.user.UserEntity

/**
 * Author: huangtao
 * Date: 2023/1/19
 * Desc: 登录的数据源接口
 */
interface UserDataSource : DataSource {

    /**
     * 登录
     */
    suspend fun signIn(username: String, password: String): Result<BaseEntity<UserEntity>>

    /**
     * 登出
     */
    suspend fun signOut(): Result<BaseEntity<Unit>>

    /**
     * 注册
     */
    suspend fun register(username: String, password: String): Result<BaseEntity<Unit>>

    /**
     * 保存用户信息
     */
    suspend fun saveUserInfo():Result<Unit>

    /**
     * 清除用户信息
     */
    suspend fun clearUserInfo():Result<Unit>

    /**
     * 获取用户信息
     */
    suspend fun obtainUserInfo():Result<Unit>
}
