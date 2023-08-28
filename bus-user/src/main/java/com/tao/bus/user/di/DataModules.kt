package com.tao.bus.user.di

import android.content.Context
import com.example.base.data.user.UserShareRepository
import com.example.base.di.IoDispatcher
import com.example.base.di.Local
import com.example.base.di.Remote
import com.example.base.network.service.UserService
import com.tao.bus.user.data.DefaultUserRepository
import com.tao.bus.user.data.DefaultUserShareRepository
import com.tao.bus.user.data.UserDataSource
import com.tao.bus.user.data.UserRepository
import com.tao.bus.user.data.local.LocalDataSource
import com.tao.bus.user.data.remote.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

/**
 * Author: huangtao
 * Date: 2023/1/19
 * Desc: 依赖注入
 */
@Module
@InstallIn(SingletonComponent::class)
object ShareRepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        repository: UserRepository,
    ): UserShareRepository {
        return DefaultUserShareRepository(repository)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        @Remote remoteDataSource: UserDataSource,
        @Local localDataSource: UserDataSource,
    ): UserRepository {
        return DefaultUserRepository(remoteDataSource, localDataSource)
    }
}

@Module
@InstallIn(SingletonComponent::class)
internal object DataSourceModule {

    @Remote
    @Provides
    @Singleton
    fun provideUserRemoteDataSource(
        userService: UserService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): UserDataSource {
        return RemoteDataSource(userService, ioDispatcher)
    }

    @Local
    @Provides
    @Singleton
    fun provideUserLocalDataSource(
        @ApplicationContext context: Context,
    ): UserDataSource {
        return LocalDataSource(context)
    }
}
