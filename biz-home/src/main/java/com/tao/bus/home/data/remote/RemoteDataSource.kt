package com.tao.bus.home.data.remote

import com.example.base.network.service.ArticleService
import com.tao.bus.home.data.ArticleDataSource
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val service: ArticleService,
) : ArticleDataSource {

    override suspend fun articleList(page: Int) = request {
        service.articleList(page)
    }
}