package com.example.base.navigation

import android.content.Context
import android.util.Log
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.example.base.RouterURL
import com.example.base.data.user.UserShareRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

@Interceptor(priority = 1, name = "登陆检查拦截器")
class LoginInterceptor : IInterceptor {

    companion object {
        const val TAG = "LoginInterceptor"
    }

    private var context: Context? = null

    override fun init(context: Context?) {
        this.context = context
    }

    override fun process(postcard: Postcard?, callback: InterceptorCallback?) {
        if (postcard?.path == RouterURL.LOGIN){
            callback?.onContinue(postcard)
            return
        }
        context?.let {
            val repository = EntryPointAccessors.fromApplication<UserShareRepositoryEntryPoint>(it).injectRepository()
            val userEntity = repository.obtainUserInfoFlow().value
            if (userEntity == null) {
                callback?.onInterrupt(NotLoginException())
            }
        }
        Log.i(TAG, "process $postcard")
        callback?.onContinue(postcard)
    }

    /**
     * 无法直接注入
     * 采用接口注入进来
     */
    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface UserShareRepositoryEntryPoint {
        fun injectRepository(): UserShareRepository
    }
}