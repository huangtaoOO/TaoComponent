package com.example.base.entity.user

import androidx.annotation.Keep

/**
 * Author: huangtao
 * Date: 2023/1/19
 * Desc: 用户实体类
 *
 * eg:
{"admin":false,"chapterTops":[],"coinCount":10,
"collectIds":[],"email":"","icon":"","id":144156,
"nickname":"坤坤993","password":"","publicName":"坤坤993",
"token":"","type":0,"username":"坤坤993"}
 */
@Keep
data class UserEntity(
    val id: Int,
    val nickname: String,
    val password: String,
    val publicName: String,
    val username: String
)
