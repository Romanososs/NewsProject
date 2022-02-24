package com.example.newsproject.ui.newsList

import android.util.Log
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

    override val list: MutableLiveData<MutableList<News>> = MutableLiveData(mutableListOf())
    override val state: MutableLiveData<ScreenState> = MutableLiveData(ScreenState.IsLoading)
    override val errorMessage: MutableLiveData<String> = MutableLiveData("")

    //private val categoryId = savedState.get<Long>("categoryId") ?: -1

    //if nextPage == null -> last page loading returned empty list aka it's the last page
    private var nextPage: Int? = 0

    init {
        Log.d(TAG, "was initialized")
        getNewPage()
    }

    /**
        Load next page, viewModel page count will be increased by 1
        Call it from Fragment, when user scrolled to the end of the recycler
     */
    override fun getNewPage() {
        if (nextPage != null)
            viewModelScope.launch {
                try {
                    val curPage = repository.getNewsList(categoryId, nextPage!!)
                    if (curPage.isNotEmpty()) {
                        val array = list.value ?: mutableListOf()
                        array.addAll(curPage)
                        list.value = array
                        nextPage = nextPage!! + 1
                    } else
                        nextPage = null
                    state.value = ScreenState.IsReady
                } catch (t: Throwable) {
                    Log.d(TAG, "caught throwable '${t.message}'")
                    errorMessage.value = t.message
                    state.value = ScreenState.IsFailed
                }
            }
    }

}