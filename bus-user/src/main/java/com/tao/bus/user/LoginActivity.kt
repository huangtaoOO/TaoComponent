package com.tao.bus.user

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.base.BaseActivity
import com.example.base.RouterURL
import com.example.lib_ktx.viewbinding.binding
import com.tao.bus.user.databinding.ActivityLoginBinding

@Route(path = RouterURL.LOGIN)
class LoginActivity : BaseActivity() {

    private val binding by binding<ActivityLoginBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding

    }
}