package com.tao.bus.user.data.remote

import com.example.base.data.Result
import com.example.base.entity.user.UserEntity
import com.example.base.network.service.UserService
import com.tao.bus.user.data.UserDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * Author: huangtao
 * Date: 2023/1/19
 * Desc: 网络数据源
 */
class RemoteDataSource(
    private val service: UserService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : UserDataSource {

    override suspend fun signIn(username: String, password: String) = withContext(ioDispatcher) {
        try {
            return@withContext Result.Success(service.login(username, password))
        } catch (e: Exception) {
            return@withContext Result.Error(e)
        }
    }

    override suspend fun signOut() = withContext(ioDispatcher) {
        try {
            return@withContext Result.Success(service.logout())
        } catch (e: Exception) {
            return@withContext Result.Error(e)
        }
    }

    override suspend fun register(username: String, password: String) = withContext(ioDispatcher) {
        try {
            return@withContext Result.Success(service.register(username, password, password))
        } catch (e: Exception) {
            return@withContext Result.Error(e)
        }
    }

    override suspend fun saveUserInfo(entity: UserEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun clearUserInfo() {
        TODO("Not yet implemented")
    }

    override suspend fun obtainUserInfo(): Result<UserEntity> {
        TODO("Not yet implemented")
    }
}