package com.tao.bus.home.ui.home

import com.example.base.entity.home.ArticleEntity
import com.example.lib_widget.LoadingLayout

data class HomeUIState(
    val loadState: LoadingLayout.State,
    val currentPage: Int,
    val over: Boolean,
    val dataList: List<ArticleEntity>
)
