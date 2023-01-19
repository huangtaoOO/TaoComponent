package com.tao.bus.user

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.base.base.BaseActivity
import com.example.base.RouterURL
import com.example.base.network.entity.NetCode
import com.example.base.network.service.UserService
import com.example.lib_ktx.viewbinding.binding
import com.tao.bus.user.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@Route(path = RouterURL.LOGIN)
@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    private val binding by binding<ActivityLoginBinding>()

    @Inject
    internal lateinit var uerService: UserService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btLogin.setOnClickListener {
            lifecycleScope.launchWhenResumed {
                text(Dispatchers.IO)
            }
        }
    }

    private suspend fun text(context: CoroutineContext) {
        withContext(context) {
            val response = uerService.login(binding.edName.toString(), binding.edPassword.toString())
            Log.i("LoginActivity","返回的数据=$response")
            when (response.errorCode) {
                NetCode.ERROR.value -> {

                }
                NetCode.NORMAL.value -> {

                }
            }
        }
    }

}