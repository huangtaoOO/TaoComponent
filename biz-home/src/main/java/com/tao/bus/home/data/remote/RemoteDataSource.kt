package com.tao.bus.home.data.remote

import com.example.base.network.service.ArticleService
import com.tao.bus.home.data.ArticleDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class RemoteDataSource(
    private val service: ArticleService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ArticleDataSource {

    override suspend fun articleList(page: Int) = request {
        service.articleList(page)
    }
}