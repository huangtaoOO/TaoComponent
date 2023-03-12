package com.tao.bus.user.data

import android.util.Log
import com.example.base.entity.BaseEntity
import com.example.base.entity.user.UserEntity as UserEntity

/**
 * Author: huangtao
 * Date: 2023/1/19
 * Desc: 默认LoginRepository实现
 */
class DefaultUserRepository(
    private val remoteDataSource: UserDataSource,
    private val localDataSource: UserDataSource,
) : UserRepository {

    override suspend fun signIn(
        username: String,
        password: String
    ): Result<BaseEntity<UserEntity>> {
        val result = remoteDataSource.signIn(username, password)
        if (result.isSuccess && result.getOrThrow().isSuccess) {
            saveUserInfo(result.getOrThrow().data)
        }
        return result
    }

    override suspend fun signOut(): Result<BaseEntity<Unit>> {
        val result = remoteDataSource.signOut()
        if (result.isSuccess) {
            clearUserInfo()
        }
        return result
    }

    override suspend fun register(username: String, password: String): Result<BaseEntity<Unit>> {
        return remoteDataSource.register(username, password)
    }

    override suspend fun saveUserInfo(entity: UserEntity) {
        localDataSource.saveUserInfo(entity)
    }

    override suspend fun clearUserInfo() {
        localDataSource.clearUserInfo()
    }

    override suspend fun obtainUserInfo(): Result<UserEntity> {
        return localDataSource.obtainUserInfo()
    }
}