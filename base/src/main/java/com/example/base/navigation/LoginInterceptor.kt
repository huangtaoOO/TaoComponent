package com.example.base.navigation

import android.content.Context
import android.util.Log
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor

@Interceptor(priority = 1, name = "登陆检查拦截器")
class LoginInterceptor : IInterceptor {

    override fun init(context: Context?) {

    }

    override fun process(postcard: Postcard?, callback: InterceptorCallback?) {
        Log.i("测试","$postcard")
        callback?.onContinue(postcard)
    }
}