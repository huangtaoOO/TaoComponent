package com.tao.bus.home.di

import android.content.Context
import com.example.base.di.IoDispatcher
import com.example.base.di.Local
import com.example.base.di.Remote
import com.example.base.network.service.ArticleService
import com.tao.bus.home.data.ArticleDataSource
import com.tao.bus.home.data.ArticleRepository
import com.tao.bus.home.data.DefaultUserRepository
import com.tao.bus.home.data.local.LocalDataSource
import com.tao.bus.home.data.remote.RemoteDataSource
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
object RepositoryModule {

    @Provides
    @Singleton
    fun provideArticlesRepository(
        @Remote remoteDataSource: ArticleDataSource,
        @Local localDataSource: ArticleDataSource
    ): ArticleRepository {
        return DefaultUserRepository(remoteDataSource, localDataSource)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Remote
    @Provides
    @Singleton
    fun provideArticleRemoteDataSource(
        articleService: ArticleService,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): ArticleDataSource {
        return RemoteDataSource(articleService, ioDispatcher)
    }

    @Local
    @Provides
    @Singleton
    fun provideArticleLocalDataSource(
        @ApplicationContext context: Context
    ): ArticleDataSource {
        return LocalDataSource(context)
    }
}
