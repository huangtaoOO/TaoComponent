package com.tao.bus.home.data

import com.example.base.data.DataSource
import com.example.base.entity.BaseEntity
import com.example.base.entity.ListEntity
import com.example.base.entity.home.ArticleEntity

interface ArticleDataSource :DataSource {

    /**
     * 获取文章列表
     * @param page 页码
     * @return 文章列表
     */
    suspend fun articleList(page :Int):Result<BaseEntity<ListEntity<ArticleEntity>>>
}