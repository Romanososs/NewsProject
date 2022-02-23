package com.example.newsproject.ui.news

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.newsproject.data.News
import com.example.newsproject.data.NewsRepository
import com.example.newsproject.ui.FragmentState

class NewsViewModelImpl(
    private val repository: NewsRepository,
    private val savedState: SavedStateHandle
) : ViewModel(),
    NewsViewModel {
    private val TAG = "MyNewsViewModel"
    override val news: MutableLiveData<News> = MutableLiveData()
    override val state: MutableLiveData<FragmentState> = MutableLiveData()
    override val errorMessage: MutableLiveData<String> = MutableLiveData()

    private val newsId = savedState.get<Long>("newsId") ?: -1

    init {
        Log.d(TAG, "was initialized")
        state.value = FragmentState.isLoading
        repository.getNews(
            newsId,
            onSuccess = {
                Log.d(TAG, "getNews onSuccess called")
                news.value = it
            },
            onPartialSuccess = {
                Log.d(TAG, "getNews onPartialSuccess called")
                news.value = it
                state.value = FragmentState.isReady
            },
            onFailure = {
                Log.d(TAG, "getNews onFailure called")
                errorMessage.value = it
                state.value = FragmentState.isFailed
            }
        )
    }
}