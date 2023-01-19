package com.tao.bus.user.data.local

import com.example.base.data.Result
import com.example.base.entity.BaseEntity
import com.example.base.entity.NetCode
import com.example.base.entity.user.UserEntity
import com.tao.bus.user.data.UserDataSource

/**
 * Author: huangtao
 * Date: 2023/1/19
 * Desc: 本地数据源
 */
class LocalDataSource : UserDataSource {
    override suspend fun signIn(
        username: String,
        password: String
    ): Result<BaseEntity<UserEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun signOut(): Result<BaseEntity<Unit>> {
        TODO("Not yet implemented")
    }

    override suspend fun register(username: String, password: String): Result<BaseEntity<Unit>> {
        TODO("Not yet implemented")
    }

    override suspend fun saveUserInfo(): Result<Unit> {
        return Result.Success(Unit)
    }

    override suspend fun clearUserInfo(): Result<Unit> {
        return Result.Success(Unit)
    }

    override suspend fun obtainUserInfo(): Result<Unit> {
        return Result.Success(Unit)
    }
}