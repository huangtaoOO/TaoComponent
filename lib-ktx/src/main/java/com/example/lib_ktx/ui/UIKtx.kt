package com.example.lib_ktx.ui

import android.content.Context
import android.widget.Toast

/**
 * Author: huangtao
 * Date: 2023/1/23
 * Desc: View的一些扩展
 */

fun Context.showToast(str : String){
    Toast.makeText(
        this,
        str,
        Toast.LENGTH_LONG
    ).show()
}