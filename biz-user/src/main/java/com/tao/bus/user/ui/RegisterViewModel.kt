package com.tao.bus.user.ui

import androidx.lifecycle.viewModelScope
import com.example.base.base.BaseViewModel
import com.example.base.entity.BaseEntity
import com.tao.bus.user.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Author: huangtao
 * Date: 2023/1/23
 * Desc: 注册的viewModel
 */
@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: UserRepository
) : BaseViewModel() {

    private val mRegisterFlow = MutableStateFlow<Result<Int>?>(null)
    val registerFlow: Flow<Result<Int>?> get() = mRegisterFlow

    fun register(name: String, password: String, rePassword: String) {
        viewModelScope.launch {
            if (password != rePassword) {
                return@launch
            }
            val result = repository.register(name,password)
            if (result.isSuccess){
                mRegisterFlow.value = Result.success(result.getOrThrow().errorCode)
            }else{
                mRegisterFlow.value = Result.failure(result.exceptionOrNull()?:NullPointerException("异常"))
            }
        }
    }


}