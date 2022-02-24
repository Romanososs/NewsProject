package com.example.newsproject.ui.news

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsproject.data.News
import com.example.newsproject.data.NewsRepository
import com.example.newsproject.ui.screenState.ScreenState
import kotlinx.coroutines.launch

class NewsViewModelImpl (
    private val repository: NewsRepository,
    private val newsId: Long
) : ViewModel(),
    NewsViewModel {
    private val TAG = "MyNewsViewModel"

    override val news: MutableLiveData<News> = MutableLiveData()
    override val state: MutableLiveData<ScreenState> = MutableLiveData(ScreenState.IsLoading)
    override val errorMessage: MutableLiveData<String> = MutableLiveData("")

    //private val newsId = savedState.get<Long>("newsId") ?: -1

    init {
        viewModelScope.launch {
            Log.d(TAG, "was initialized")
            news.value = repository.getQuickNews(newsId)
            state.value = ScreenState.IsReady
            news.value = repository.getNews(newsId)
        }
    }
}