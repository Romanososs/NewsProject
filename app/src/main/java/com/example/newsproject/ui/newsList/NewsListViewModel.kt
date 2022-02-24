package com.example.newsproject.ui.newsList

import androidx.lifecycle.MutableLiveData
import com.example.newsproject.data.News
import com.example.newsproject.ui.screenState.ScreenState

interface NewsListViewModel {
    val list: MutableLiveData<MutableList<News>>
    val state: MutableLiveData<ScreenState>
    val errorMessage: MutableLiveData<String>

    fun getNewPage()
}