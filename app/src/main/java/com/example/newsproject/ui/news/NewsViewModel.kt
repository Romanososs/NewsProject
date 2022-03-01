package com.example.newsproject.ui.news

import androidx.compose.runtime.MutableState
import com.example.newsproject.data.News
import com.example.newsproject.ui.screenState.ScreenState

interface NewsViewModel {
    val news: MutableState<News>
    val state: MutableState<ScreenState>
    var errorMessage: String
}