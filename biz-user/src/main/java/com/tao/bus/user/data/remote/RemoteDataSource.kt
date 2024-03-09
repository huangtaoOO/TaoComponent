package com.tao.bus.user.data.remote

import com.example.base.network.service.UserService
import com.tao.bus.user.data.UserDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Author: huangtao
 * Date: 2023/1/19
 * Desc: 网络数据源
 */
class RemoteDataSource(
    private val service: UserService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : UserDataSource {

    override suspend fun signIn(username: String, password: String) = request {
        service.login(username, password)
    }

    override suspend fun signOut() = request {
        service.logout()
    }

    override suspend fun register(username: String, password: String) = request {
        service.register(username, password, password)
    }

}