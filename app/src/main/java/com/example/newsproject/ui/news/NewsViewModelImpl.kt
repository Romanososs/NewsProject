package com.example.newsproject.ui.news

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsproject.data.News
import com.example.newsproject.data.NewsRepository
import com.example.newsproject.ui.FragmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModelImpl @Inject constructor(
    private val repository: NewsRepository,
    private val savedState: SavedStateHandle
) : ViewModel(),
    NewsViewModel {
    private val TAG = "MyNewsViewModel"
    override val news: MutableLiveData<News> = MutableLiveData()
    override val state: MutableLiveData<FragmentState> = MutableLiveData(FragmentState.isLoading)
    override val errorMessage: MutableLiveData<String> = MutableLiveData("")

    private val newsId = savedState.get<Long>("newsId") ?: -1

    init {
        viewModelScope.launch {
            Log.d(TAG, "was initialized")
            news.value = repository.getQuickNews(newsId)
            state.value = FragmentState.isReady
            news.value = repository.getNews(newsId)
        }
    }
}