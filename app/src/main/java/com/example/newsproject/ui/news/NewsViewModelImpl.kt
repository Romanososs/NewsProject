package com.example.newsproject.ui.news

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsproject.data.News
import com.example.newsproject.data.NewsRepository
import com.example.newsproject.ui.screenState.ScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NewsViewModelImpl (
    private val repository: NewsRepository,
    private val newsId: Long
) : ViewModel(),
    NewsViewModel {
    private val TAG = "MyNewsViewModel"

    override val news: MutableState<News> = mutableStateOf(News())
    override val state: MutableState<ScreenState> = mutableStateOf(ScreenState.IsLoading)
    override var errorMessage: String = ""

    init {
        viewModelScope.launch {
            Log.d(TAG, "was initialized")
            repository.getNewsFromCache(newsId).collect {
                news.value = it
                state.value = ScreenState.IsReady
            }
            repository.getNews(newsId).collect {
                news.value = it
            }
        }
    }
}