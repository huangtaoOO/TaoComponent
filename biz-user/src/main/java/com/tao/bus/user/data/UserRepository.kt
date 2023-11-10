package com.tao.bus.user.data

import com.example.base.data.Repository
import com.example.base.entity.BaseEntity
import com.example.base.entity.user.UserEntity
import kotlinx.coroutines.flow.StateFlow

/**
 * Author: huangtao
 * Date: 2023/1/19
 * Desc: 登录的数据接口
 */
interface UserRepository : Repository {

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
    suspend fun saveUserInfo(entity: UserEntity)

    /**
     * 清除当前用户信息
     */
    suspend fun clearUserInfo()

    /**
     * 获取用户信息
     */
    suspend fun obtainUserInfo(): Result<UserEntity>

    /**
     * 获取用户信息流
     * 未登录为Null
     */
    fun obtainUserInfoFlow(): StateFlow<UserEntity?>

    /**
     * 是否登录
     */
    fun isLogin(): Boolean
}
