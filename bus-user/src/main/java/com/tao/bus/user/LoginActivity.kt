package com.tao.bus.user

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.base.base.BaseActivity
import com.example.base.RouterURL
import com.example.lib_ktx.ui.click
import com.example.lib_ktx.viewbinding.binding
import com.tao.bus.user.constant.Constant
import com.tao.bus.user.databinding.ActivityLoginBinding
import com.tao.bus.user.ui.LoginViewModel
import com.tao.bus.user.utils.CheckUtils
import dagger.hilt.android.AndroidEntryPoint

@Route(path = RouterURL.LOGIN)
@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    private val binding: ActivityLoginBinding by binding()

    private val mViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenResumed {
            binding.edName.setText(mViewModel.obtainUserName())
            mViewModel.loginFlow.collect {
                if (it == null) return@collect
                Toast.makeText(this@LoginActivity, "返回的数据=$it", Toast.LENGTH_LONG).show()
            }
        }

        binding.btLogin.click {
            loginLogic()
        }

        binding.btToRegister.click {
            ARouter.getInstance().build(RouterURL.REGISTER).navigation()
            finish()
        }

        binding.btLogOut.click {
            mViewModel.logout()
        }
    }

    private fun loginLogic() {
        val name = binding.edName.text.toString().trim()
        val password = binding.edPassword.text.toString().trim()
        if (!CheckUtils.checkName(name)) {
            Toast.makeText(this, "用户名${Constant.NAME_LENGTH}位", Toast.LENGTH_LONG).show()
            return
        }
        if (!CheckUtils.checkPassWord(password)) {
            Toast.makeText(this, "密码${Constant.PASSWORD_LENGTH}位", Toast.LENGTH_LONG).show()
            return
        }
        mViewModel.login(name, password)
    }

}