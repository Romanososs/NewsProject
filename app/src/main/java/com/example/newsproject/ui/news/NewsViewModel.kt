package com.example.newsproject.ui.news

import androidx.lifecycle.MutableLiveData
import com.example.newsproject.data.News
import com.example.newsproject.ui.screenState.ScreenState

interface NewsViewModel {
    val news: MutableLiveData<News>
    val state: MutableLiveData<ScreenState>
    val errorMessage: MutableLiveData<String>
}