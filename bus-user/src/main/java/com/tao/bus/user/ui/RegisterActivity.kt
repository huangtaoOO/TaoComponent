package com.tao.bus.user.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.base.data.Result
import com.example.base.RouterURL
import com.example.base.base.BaseActivity
import com.example.base.entity.NetCode
import com.example.lib_ktx.ui.click
import com.example.lib_ktx.ui.showToast
import com.example.lib_ktx.viewbinding.binding
import com.tao.bus.user.constant.Constant
import com.tao.bus.user.databinding.ActivityRegisterBinding
import com.tao.bus.user.utils.CheckUtils
import dagger.hilt.android.AndroidEntryPoint

/**
 * Author: huangtao
 * Date: 2023/1/23
 * Desc: 注册界面
 */

@Route(path = RouterURL.REGISTER)
@AndroidEntryPoint
class RegisterActivity : BaseActivity() {

    private val binding: ActivityRegisterBinding by binding()

    private val mViewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenResumed {
            mViewModel.registerFlow.collect {
                if (it == null) return@collect
                when (it) {
                    is Result.Success<Int> -> {
                        if (it.data == NetCode.NORMAL.value) {
                            showToast("注册成功")
                            ARouter.getInstance().build(RouterURL.LOGIN).navigation()
                            finish()
                        } else {
                            showToast("注册失败错误:${it.data}")
                        }
                    }
                    is Result.Error -> {
                        showToast("注册失败错误:${it.exception.message}")
                    }
                }
            }
        }

        binding.btToLogin.click {
            ARouter.getInstance().build(RouterURL.REGISTER).navigation()
            finish()
        }

        binding.btRegister.click {
            registerLogic()
        }
    }

    private fun registerLogic() {
        val name = binding.edName.text.toString().trim()
        val password = binding.edPassword.text.toString().trim()
        val rePassword = binding.edRePassword.text.toString().trim()

        if (!CheckUtils.checkName(name)) {
            Toast.makeText(this, "用户名${Constant.NAME_LENGTH}位", Toast.LENGTH_LONG).show()
            return
        }
        if (!CheckUtils.checkPassWord(password)) {
            Toast.makeText(this, "密码${Constant.PASSWORD_LENGTH}位", Toast.LENGTH_LONG).show()
            return
        }
        if (password != rePassword) {
            Toast.makeText(this, "两次输入密码不一致", Toast.LENGTH_LONG).show()
            return
        }
        mViewModel.register(name, password, rePassword)
    }

}