package com.example.base.network.service

import com.example.base.entity.BaseEntity
import com.example.base.entity.ListEntity
import com.example.base.entity.home.ArticleEntity
import com.example.base.network.NetUrlConst
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Author: huangtao
 * Date: 2023/3/12
 * Desc: 用户模块接口
 */
interface ArticleService {

    @GET(NetUrlConst.ARTICLE_LIST)
    suspend fun articleList(@Path("index") index: Int): BaseEntity<ListEntity<ArticleEntity>>
}