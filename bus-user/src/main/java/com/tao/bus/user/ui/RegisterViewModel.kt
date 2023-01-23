package com.tao.bus.user.ui

import androidx.lifecycle.viewModelScope
import com.example.base.base.BaseViewModel
import com.example.base.data.Result
import com.example.base.entity.BaseEntity
import com.tao.bus.user.data.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Author: huangtao
 * Date: 2023/1/23
 * Desc: 注册的viewModel
 */
class RegisterViewModel constructor(
    @Inject private val repository: UserRepository
) : BaseViewModel() {

    private val mRegisterFlow = MutableStateFlow<Result<Int>?>(null)
    val registerFlow: Flow<Result<Int>?> get() = mRegisterFlow

    fun register(name: String, password: String, rePassword: String) {
        viewModelScope.launch {
            if (password != rePassword) {
                return@launch
            }
            when (val result = repository.register(name, password)) {
                is Result.Success<BaseEntity<Unit>> -> {
                    mRegisterFlow.value = Result.Success(result.data.errorCode)
                }
                is Result.Error -> {
                    mRegisterFlow.value = result
                }
            }
        }
    }


}