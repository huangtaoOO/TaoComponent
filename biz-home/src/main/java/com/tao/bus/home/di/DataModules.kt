package com.tao.bus.home.di

import com.example.base.di.Local
import com.example.base.di.Remote
import com.tao.bus.home.data.ArticleDataSource
import com.tao.bus.home.data.ArticleRepository
import com.tao.bus.home.data.DefaultUserRepository
import com.tao.bus.home.data.local.LocalDataSource
import com.tao.bus.home.data.remote.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * Author: huangtao
 * Date: 2023/1/19
 * Desc: 依赖注入
 */

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {


    @Binds
    abstract fun provideArticlesRepository(
        defaultUserRepository: DefaultUserRepository
    ): ArticleRepository
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataSourceModule {

    @Remote
    @Binds
    abstract fun provideArticleRemoteDataSource(
        remoteDataSource: RemoteDataSource
    ): ArticleDataSource


    @Local
    @Binds
    abstract fun provideArticleLocalDataSource(
        localDataSource: LocalDataSource
    ): ArticleDataSource
}
