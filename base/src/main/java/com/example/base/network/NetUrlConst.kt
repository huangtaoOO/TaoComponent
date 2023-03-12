package com.example.base.network

/**
 * Author: huangtao
 * Date: 2023/1/19
 * Desc: 网络相关常量
 */
object NetUrlConst {

    const val BASE_URL = "https://www.wanandroid.com"

    /**
     * 登录
     */
    const val LOGIN = "/user/login"

    /**
     * 注册
     */
    const val REGISTER = "/user/register"

    /**
     * 登出
     */
    const val LOGOUT = "user/logout/json"

    /**
     * 首页文章列表
     */
    const val ARTICLE_LIST = "/article/list/{index}/json"
}