package com.tao.bus.user.data

import android.util.Log
import com.example.base.entity.BaseEntity
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.concurrent.atomic.AtomicBoolean
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

    private val isLogin = AtomicBoolean(false)

    override suspend fun signIn(
        username: String,
        password: String,
    ): Result<BaseEntity<UserEntity>> {
        val result = remoteDataSource.signIn(username, password)
        if (result.isSuccess && result.getOrThrow().isSuccess) {
            isLogin.set(true)
            saveUserInfo(result.getOrThrow().data)
        }
        return result
    }

    override suspend fun signOut(): Result<BaseEntity<Unit>> {
        val result = remoteDataSource.signOut()
        if (result.isSuccess) {
            clearUserInfo()
            isLogin.set(false)
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

    override fun obtainUserInfoFlow(): StateFlow<UserEntity?> {
        val initValue = localDataSource.obtainUserInfoFlow().value
        return localDataSource.obtainUserInfoFlow().map {
            if (isLogin()) {
                it
            } else {
                null
            }
        }.stateIn(
            scope = MainScope(),
            started = SharingStarted.WhileSubscribed(0),
            initialValue = if (isLogin()) initValue else null
        )
    }

    override fun isLogin(): Boolean {
        return isLogin.get()
    }

}