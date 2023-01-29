package com.tao.bus.user.di

import com.example.base.di.IoDispatcher
import com.example.base.di.Local
import com.example.base.di.Remote
import com.example.base.network.service.UserService
import com.tao.bus.user.data.DefaultUserRepository
import com.tao.bus.user.data.UserDataSource
import com.tao.bus.user.data.UserRepository
import com.tao.bus.user.data.local.LocalDataSource
import com.tao.bus.user.data.remote.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Author: huangtao
 * Date: 2023/1/19
 * Desc: 依赖注入
 */

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideTasksRepository(
        @Remote remoteDataSource: UserDataSource,
        @Local localDataSource: UserDataSource
    ): UserRepository {
        return DefaultUserRepository(remoteDataSource, localDataSource)
    }
}

@Module
@InstallIn(ViewModelComponent::class)
object DataSourceModule {

    @Remote
    @Provides
    fun provideUserRemoteDataSource(
        userService: UserService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): UserDataSource {
        return RemoteDataSource(userService, ioDispatcher)
    }

    @Local
    @Provides
    fun provideTasksLocalDataSource(): UserDataSource {
        return LocalDataSource()
    }
}
