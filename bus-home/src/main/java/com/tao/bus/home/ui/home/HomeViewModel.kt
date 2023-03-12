package com.tao.bus.home.ui.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.base.base.BaseViewModel
import com.example.base.data.user.UserShareRepository
import com.tao.bus.home.data.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: ArticleRepository,
                                        private val userRepository: UserShareRepository)
    : BaseViewModel() {

    private val _text = MutableStateFlow("This is home Fragment")
    val text: Flow<String> get() = _text

    init {
        viewModelScope.launch {
            _text.value = repository.articleList(0).getOrThrow().toString()
            Log.i("测试","获取用户名：${userRepository.obtainUserInfoFlow().value}")
        }
    }

}