package com.example.newsproject.ui.categoryList

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.example.newsproject.utils.SingleLiveEvent
import com.example.newsproject.data.Category
import com.example.newsproject.data.NewsRepository
import com.example.newsproject.ui.FragmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryListViewModelImpl @Inject constructor(
    private val repository: NewsRepository
) :
    ViewModel(),
    CategoryListViewModel {
    private val TAG = "MyCategoryListViewModel"
    override val list: MutableLiveData<List<Category>> = MutableLiveData()
    override val navEvent: SingleLiveEvent<NavDirections> = SingleLiveEvent()
    override val state: MutableLiveData<FragmentState> = MutableLiveData(FragmentState.isLoading)
    override val errorMessage: MutableLiveData<String> = MutableLiveData("")

    init {
        viewModelScope.launch {
            Log.d(TAG, "was initialized")
            try {
                list.value = repository.getCategoryList()
                state.value = FragmentState.isReady
            } catch (t: Throwable) {
                Log.d(TAG, "caught throwable '${t.message}'")
                errorMessage.value = t.message
                state.value = FragmentState.isFailed
            }
        }
    }


    override fun onItemClicked(categoryId: Long) {
        Log.d(TAG, "onItemClicked called")
        navEvent.value = CategoryListFragmentDirections
            .actionCategoryListFragmentToNewsListFragment(categoryId)
    }
}