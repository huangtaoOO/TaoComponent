package com.tao.bus.user

import androidx.lifecycle.viewModelScope
import com.example.base.base.BaseViewModel
import com.example.base.entity.BaseEntity
import com.tao.bus.user.data.UserRepository
import com.example.base.data.Result
import com.example.base.entity.user.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Author: huangtao
 * Date: 2023/1/19
 * Desc: 登录ViewModel
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: UserRepository
) : BaseViewModel() {

    //用户名默认长度
    val nameLength = 4

    //密码默认长度
    val passwordLength = 8

    private val mLoginFlow = MutableStateFlow<Result<BaseEntity<UserEntity>>?>(null)
    val loginFlow: Flow<Result<BaseEntity<UserEntity>>?> get() = mLoginFlow

    fun login(name: String, password: String) {
        viewModelScope.launch {
            mLoginFlow.value = repository.signIn(name, password)
        }
    }

    fun checkName(name: String): Boolean {
        return name.isNotEmpty() && name.length >= nameLength
    }

    fun checkPassWord(password: String): Boolean {
        return password.isNotEmpty() && password.length >= passwordLength
    }
}