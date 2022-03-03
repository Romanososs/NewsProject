package com.example.newsproject.ui.newsList

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.newsproject.data.News
import com.example.newsproject.ui.screenState.ScreenState

interface NewsListViewModel {
    val list: SnapshotStateList<News>
    val state: MutableState<ScreenState>
    val page: MutableState<Int>
    var errorMessage: String
    fun getNewPage()
}