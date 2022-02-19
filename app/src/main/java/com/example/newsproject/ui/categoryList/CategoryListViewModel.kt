package com.example.newsproject.ui.categoryList

import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.example.newsproject.data.Category
import com.example.newsproject.ui.FragmentState
import com.example.newsproject.utils.SingleLiveEvent

interface CategoryListViewModel {
    val list: MutableLiveData<List<Category>>
    val navEvent: SingleLiveEvent<NavDirections>
    val state: MutableLiveData<FragmentState>
    val errorMessage: MutableLiveData<String>
    fun onItemClicked(categoryId: Long)
}