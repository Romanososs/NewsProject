package com.example.newsproject.ui.categoryList

import androidx.compose.runtime.MutableState
import com.example.newsproject.data.Category
import com.example.newsproject.ui.screenState.ScreenState

interface CategoryListViewModel {
    val list: MutableState<List<Category>>
    val state: MutableState<ScreenState>
    var errorMessage: String
}