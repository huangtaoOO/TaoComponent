package com.tao.bus.home.data

import com.example.base.entity.BaseEntity
import com.example.base.entity.ListEntity
import com.example.base.entity.home.ArticleEntity

class DefaultUserRepository(
    private val remoteDataSource: ArticleDataSource,
    private val localDataSource: ArticleDataSource,
) : ArticleRepository {

    override suspend fun articleList(page: Int): Result<BaseEntity<ListEntity<ArticleEntity>>> {
        return remoteDataSource.articleList(page)
    }
}