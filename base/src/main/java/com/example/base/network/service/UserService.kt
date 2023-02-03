package com.example.base.network.service

import com.example.base.network.NetUrlConst
import com.example.base.entity.BaseEntity
import com.example.base.entity.user.UserEntity
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Author: huangtao
 * Date: 2023/1/19
 * Desc: 用户模块接口
 */
interface UserService {

    @POST(NetUrlConst.LOGIN)
    suspend fun login(
       @Query("username") username: String,
       @Query("password") password: String): BaseEntity<UserEntity>

    @POST(NetUrlConst.REGISTER)
    suspend fun register(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("repassword") rePassword: String): BaseEntity<Unit>


    @GET(NetUrlConst.LOGOUT)
    suspend fun logout(): BaseEntity<Unit>
}