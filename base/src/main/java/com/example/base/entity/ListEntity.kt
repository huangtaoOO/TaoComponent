package com.example.base.entity

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * Author: huangtao
 * Date: 2023/3/12
 * Desc: 分页实体
 */
@Keep
data class ListEntity<T>(
    val curPage: Int,
    @SerializedName("datas")
    val data: List<T>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)