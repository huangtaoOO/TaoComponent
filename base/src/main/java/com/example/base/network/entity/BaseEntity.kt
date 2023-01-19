package com.example.base.network.entity

/**
 * Author: huangtao
 * Date: 2023/1/19
 * Desc: 返回实体基类
 */
data class BaseEntity<T>(
    val data : T,
    val errorCode : Int,
    val errorMsg : String
)
