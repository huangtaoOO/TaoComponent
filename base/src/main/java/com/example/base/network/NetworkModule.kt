package com.example.base.network

import android.content.Context
import android.util.Log
import com.example.base.network.service.ArticleService
import com.example.base.network.service.UserService
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Author: huangtao
 * Date: 2023/1/19
 * Desc: 网络相关Client生成
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val TAG = "NetworkModule"

    @Provides
    @Singleton
    fun providesOKHttpClient(@ApplicationContext context: Context): OkHttpClient {
        Log.i(TAG, "providesOKHttpClient")
        val okHttpClient = OkHttpClient()
            .newBuilder()
            .addInterceptor(HttpLoggingInterceptor { message ->
                Log.i(TAG, message)
            }.setLevel(HttpLoggingInterceptor.Level.BODY))
            .cookieJar(PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(context)))
            .build()
        return okHttpClient
    }

    @Provides
    @Singleton
    fun providesRetrofit(client: OkHttpClient): Retrofit {
        Log.i(TAG, "providesRetrofit")
        return Retrofit.Builder()
            .baseUrl(NetUrlConst.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun providesNetUserService(retrofit: Retrofit): UserService {
        Log.i(TAG, "providesNetUserService")
        return retrofit.create(UserService::class.java)
    }

    @Provides
    fun providesNetArticleService(retrofit: Retrofit): ArticleService {
        Log.i(TAG, "providesNetUserService")
        return retrofit.create(ArticleService::class.java)
    }
}