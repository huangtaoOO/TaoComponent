package com.tao.bus.home.data

import com.example.base.di.Local
import com.example.base.di.Remote
import com.example.base.entity.BaseEntity
import com.example.base.entity.ListEntity
import com.example.base.entity.home.ArticleEntity
import javax.inject.Inject

class DefaultUserRepository @Inject constructor(
    @Remote private val remoteDataSource: ArticleDataSource,
    @Local private val localDataSource: ArticleDataSource,
) : ArticleRepository {

    override suspend fun articleList(page: Int): Result<BaseEntity<ListEntity<ArticleEntity>>> {
        return remoteDataSource.articleList(page)
    }
}