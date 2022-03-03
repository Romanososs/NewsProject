package com.example.newsproject.ui.newsList

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsproject.data.News
import com.example.newsproject.data.NewsRepository
import com.example.newsproject.ui.screenState.ScreenState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NewsListViewModelImpl(
    private val repository: NewsRepository,
    private val categoryId: Long
) : ViewModel(),
    NewsListViewModel {
    private val TAG = "MyNewsListViewModel"

    override val list: SnapshotStateList<News> = mutableStateListOf()
    override val state: MutableState<ScreenState> = mutableStateOf(ScreenState.IsLoading)
    //if page == -1 => last page was loaded
    override val page: MutableState<Int> = mutableStateOf(0)
    override var errorMessage: String = ""

    init {
        Log.d(TAG, "was initialized")
        getNewPage()
    }

    /**
    Load next page, viewModel page count will be increased by 1
     */
    override fun getNewPage() {
        viewModelScope.launch {
            Log.d(TAG, "getNewPage was called with page ${page.value}")
            if (page.value != -1)
                try {
                    repository.getNewsList(categoryId, page.value).collect { listOfCurrentPage ->
                        page.value = if (listOfCurrentPage.isNotEmpty()) {
                            listOfCurrentPage.forEach { news -> list.add(news) }
                            page.value + 1
                        } else
                            -1
                        state.value = ScreenState.IsReady
                    }
                } catch (t: Throwable) {
                    Log.d(TAG, "caught throwable '${t.message}'")
                    errorMessage = t.message ?: ""
                    state.value = ScreenState.IsFailed
                }
        }
    }

}