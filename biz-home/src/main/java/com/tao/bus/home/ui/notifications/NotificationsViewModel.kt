package com.tao.bus.home.ui.notifications

import com.example.base.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class NotificationsViewModel : BaseViewModel() {

    private val _text = MutableStateFlow("This is home Fragment")
    val text: Flow<String> get() = _text
}