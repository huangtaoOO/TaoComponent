package com.tao.bus.home.ui.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class HomeViewModel : ViewModel() {

    private val _text = MutableStateFlow("This is home Fragment")
    val text: Flow<String> get() = _text

}