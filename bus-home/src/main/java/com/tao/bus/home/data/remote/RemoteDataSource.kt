package com.tao.bus.home.data.remote

import android.util.Log
import com.example.base.entity.BaseEntity
import com.example.base.entity.ListEntity
import com.example.base.entity.home.ArticleEntity
import com.example.base.network.service.ArticleService
import com.tao.bus.home.data.ArticleDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RemoteDataSource(
    private val service: ArticleService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ArticleDataSource {

    override suspend fun articleList(page: Int): Result<BaseEntity<ListEntity<ArticleEntity>>> =
        withContext(ioDispatcher) {
            try {
                return@withContext Result.success(service.articleList(page))
            } catch (e: Exception) {
                Log.e("测试","",e)
                return@withContext Result.failure(e)
            }
        }
}