package com.tao.bus.home.ui.home

import androidx.lifecycle.viewModelScope
import com.example.base.base.BaseViewModel
import com.example.base.data.user.UserShareRepository
import com.example.base.entity.home.ArticleEntity
import com.example.lib_widget.LoadingLayout
import com.tao.bus.home.data.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ArticleRepository,
    private val userRepository: UserShareRepository
) : BaseViewModel() {

    private val dataList = MutableStateFlow<List<ArticleEntity>>(emptyList())
    private val loadState = MutableStateFlow(LoadingLayout.State.Normal)
    private val currentPage = MutableStateFlow(1)
    private val over = MutableStateFlow(false)

    val uiState = combine(dataList, loadState, currentPage, over) { list, state, page, over ->
        HomeUIState(loadState = state, currentPage = page, dataList = list, over = over)
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(0),
        HomeUIState(loadState.value, currentPage.value, over.value, dataList.value)
    )

    init {
        requestData(true)
    }

    fun requestData(refresh: Boolean) {
        if (loadState.value == LoadingLayout.State.Loading) {
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            loadState.value = LoadingLayout.State.Loading
            val requestPage = if (refresh) {
                1
            } else {
                ++currentPage.value
            }
            repository.articleList(requestPage).also {
                if (it.isSuccess) {
                    val result = it.getOrThrow()
                    if (result.isSuccess) {
                        if (refresh) {
                            dataList.value = result.data.data
                        } else {
                            dataList.value += result.data.data
                        }
                        over.value = result.data.over
                        loadState.value = if (dataList.value.isNotEmpty()) {
                            LoadingLayout.State.Normal
                        } else {
                            LoadingLayout.State.EmptyData
                        }
                        currentPage.value = requestPage
                    } else {
                        loadState.value = LoadingLayout.State.Error
                    }
                } else {
                    loadState.value = LoadingLayout.State.NetworkError
                }
            }
        }
    }


}