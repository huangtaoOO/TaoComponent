package com.tao.bus.home.ui.dashboard

import androidx.lifecycle.ViewModel
import com.example.base.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class DashboardViewModel : BaseViewModel() {

    private val _text = MutableStateFlow("This is dashboard Fragment")
    val text: Flow<String> get() = _text
}