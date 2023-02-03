package com.tao.bus.user.ui

import androidx.lifecycle.viewModelScope
import com.example.base.base.BaseViewModel
import com.example.base.entity.BaseEntity
import com.tao.bus.user.data.UserRepository
import com.example.base.data.Result
import com.example.base.data.succeeded
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

    private val mLoginFlow = MutableStateFlow<Result<BaseEntity<UserEntity>>?>(null)
    val loginFlow: Flow<Result<BaseEntity<UserEntity>>?> get() = mLoginFlow

    fun login(name: String, password: String) {
        viewModelScope.launch {
            mLoginFlow.value = repository.signIn(name, password)
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.signOut()
        }
    }

    suspend fun obtainUserName(): String{
        val info = repository.obtainUserInfo()
        return if (info.succeeded){
            val name = (info as Result.Success).data.nickname
            name
        }else{
            ""
        }
    }
}