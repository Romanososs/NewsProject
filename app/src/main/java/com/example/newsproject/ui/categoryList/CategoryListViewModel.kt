package com.example.newsproject.ui.categoryList

import androidx.lifecycle.MutableLiveData
import com.example.newsproject.data.Category
import com.example.newsproject.ui.screenState.ScreenState

interface CategoryListViewModel {
    val list: MutableLiveData<List<Category>>
    val state: MutableLiveData<ScreenState>
    val errorMessage: MutableLiveData<String>
}