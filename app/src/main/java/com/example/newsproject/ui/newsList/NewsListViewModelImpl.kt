package com.example.newsproject.ui.newsList

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.example.newsproject.utils.SingleLiveEvent
import com.example.newsproject.data.News
import com.example.newsproject.data.NewsRepository
import com.example.newsproject.ui.FragmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModelImpl @Inject constructor(
    private val repository: NewsRepository,
    private val savedState: SavedStateHandle
) : ViewModel(),
    NewsListViewModel {
    private val TAG = "MyNewsListViewModel"

    override val list: MutableLiveData<MutableList<News>> = MutableLiveData()
    override val navEvent: SingleLiveEvent<NavDirections> = SingleLiveEvent()
    override val state: MutableLiveData<FragmentState> = MutableLiveData(FragmentState.isLoading)
    override val errorMessage: MutableLiveData<String> = MutableLiveData("")

    private val categoryId = savedState.get<Long>("categoryId") ?: -1

    //if nextPage == null -> last page loading returned empty list aka it's the last page
    private var nextPage: Int? = 0

    init {
        Log.d(TAG, "was initialized")
    }

    override fun onCreateView() {
        list.value = mutableListOf()
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
                    state.value = FragmentState.isReady
                } catch (t: Throwable) {
                    Log.d(TAG, "caught throwable '${t.message}'")
                    errorMessage.value = t.message
                    state.value = FragmentState.isFailed
                }
            }
    }

    override fun onDestroyView() {
        nextPage = 0
    }

    override fun onItemClicked(newsId: Long) {
        Log.d(TAG, "onItemClicked called")
        navEvent.value = NewsListFragmentDirections
            .actionNewsListFragmentToNewsFragment(newsId)
    }
}