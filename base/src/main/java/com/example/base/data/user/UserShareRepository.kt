package com.example.base.data.user

import com.example.base.data.Repository
import com.example.base.entity.user.UserEntity
import kotlinx.coroutines.flow.StateFlow

interface UserShareRepository : Repository {

    /**
     * 获取用户信息
     */
    suspend fun obtainUserInfo(): Result<UserEntity>

    /**
     * 获取用户信息流
     */
    fun obtainUserInfoFlow(): StateFlow<UserEntity?>
}