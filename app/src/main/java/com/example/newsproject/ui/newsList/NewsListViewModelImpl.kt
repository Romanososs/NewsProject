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
import kotlinx.coroutines.launch

class NewsListViewModelImpl (
    private val repository: NewsRepository,
    private val categoryId: Long
) : ViewModel(),
    NewsListViewModel {
    private val TAG = "MyNewsListViewModel"

    override val list: SnapshotStateList<News> = mutableStateListOf()
    override val state: MutableState<ScreenState> = mutableStateOf(ScreenState.IsLoading)
    override var errorMessage: String = ""

    //if nextPage == null -> last page loading returned empty list aka it's the last page
    private var nextPage: Int? = 0

    init {
        Log.d(TAG, "was initialized")
        getNewPage()
    }

    /**
        Load next page, viewModel page count will be increased by 1
     */
    override fun getNewPage() {
        if (nextPage != null)
            viewModelScope.launch {
                try {
                    val curPage = repository.getNewsList(categoryId, nextPage!!)
                    nextPage = if (curPage.isNotEmpty()) {
                        curPage.forEach { list.add(it) }
                        nextPage!! + 1
                    } else
                        null
                    state.value = ScreenState.IsReady
                } catch (t: Throwable) {
                    Log.d(TAG, "caught throwable '${t.message}'")
                    errorMessage = t.message ?: ""
                    state.value = ScreenState.IsFailed
                }
            }
    }

}