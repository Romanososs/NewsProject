package com.example.newsproject.ui.categoryList

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.newsproject.data.Category
import com.example.newsproject.data.NewsRepository
import com.example.newsproject.ui.screenState.ScreenState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CategoryListViewModelImpl(
    private val repository: NewsRepository
) :
    ViewModel(),
    CategoryListViewModel {
    private val TAG = "MyCategoryListViewModel"

    override val list: MutableState<List<Category>> = mutableStateOf(listOf())
    override val state: MutableState<ScreenState> = mutableStateOf(ScreenState.IsLoading)
    override var errorMessage: String = ""

    init {
        viewModelScope.launch {
            Log.d(TAG, "was initialized")
            try {
                repository.getCategoryList().collect() {
                    list.value = it
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