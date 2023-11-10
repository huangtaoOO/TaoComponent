package com.tao.bus.home.data.local

import android.content.Context
import com.example.base.entity.BaseEntity
import com.example.base.entity.ListEntity
import com.example.base.entity.home.ArticleEntity
import com.tao.bus.home.data.ArticleDataSource

class LocalDataSource(private val context: Context) : ArticleDataSource {

    override suspend fun articleList(page: Int): Result<BaseEntity<ListEntity<ArticleEntity>>> {
        TODO("Not yet implemented")
    }
}