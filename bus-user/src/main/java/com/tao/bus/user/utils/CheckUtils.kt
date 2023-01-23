package com.tao.bus.user.utils

import com.tao.bus.user.constant.Constant

/**
 * Author: huangtao
 * Date: 2023/1/23
 * Desc: 检验工具
 */
object CheckUtils {

    @JvmStatic
    fun checkName(name: String): Boolean {
        return name.isNotEmpty() && name.length >= Constant.NAME_LENGTH
    }

    @JvmStatic
    fun checkPassWord(password: String): Boolean {
        return password.isNotEmpty() && password.length >= Constant.PASSWORD_LENGTH
    }
}