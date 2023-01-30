package com.example.base.di

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Author: huangtao
 * Date: 2023/1/30
 * Desc: Gson注入
 */

@Module
@InstallIn(SingletonComponent::class)
object GsonModule {

    @Provides
    @IoDispatcher
    fun providesGson(): Gson {
        return Gson()
    }
}