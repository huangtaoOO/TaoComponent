package com.example.base.kt

/**
 * Author: huangtao
 * Date: 2023/1/19
 * Desc: dp sp 扩展
 */

private fun Int.dp(): Int {
    val scale = BaseApplication.instance.resources.displayMetrics.density
    return (this * scale + 0.5f).toInt()
}

private fun Int.sp(): Int {
    val scale = BaseApplication.instance.resources.displayMetrics.scaledDensity
    return (this * scale + 0.5f).toInt()
}


val Int.dp: Int
    get() = dp()

val Int.sp: Int
    get() = sp()