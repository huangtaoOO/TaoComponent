package com.tao.bus.user

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.base.base.BaseActivity
import com.example.base.RouterURL
import com.example.lib_ktx.viewbinding.binding
import com.tao.bus.user.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@Route(path = RouterURL.LOGIN)
@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    private val binding: ActivityLoginBinding by binding()

    private val mViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenResumed {
            mViewModel.loginFlow.collect {
                if (it == null) return@collect
                Toast.makeText(this@LoginActivity, "返回的数据=$it", Toast.LENGTH_LONG).show()
            }
        }

        binding.btLogin.setOnClickListener {
            loginLogic()
        }
    }

    private fun loginLogic() {
        val name = binding.edName.text.toString().trim()
        val password = binding.edPassword.text.toString().trim()
        if (!mViewModel.checkName(name)) {
            Toast.makeText(this, "用户名${mViewModel.nameLength}位", Toast.LENGTH_LONG).show()
            return
        }
        if (!mViewModel.checkPassWord(password)) {
            Toast.makeText(this, "密码${mViewModel.passwordLength}位", Toast.LENGTH_LONG).show()
            return
        }
        mViewModel.login(name, password)
    }

}