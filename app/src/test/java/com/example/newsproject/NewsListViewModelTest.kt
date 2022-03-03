package com.example.newsproject

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.newsproject.data.NewsRepository
import com.example.newsproject.ui.newsList.NewsListViewModelImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class NewsListViewModelTest {

//    private val repo: NewsRepository = NewsRepositoryFake()
//    private val categoryId = 0L
//    private val viewModel: NewsListViewModelImpl = NewsListViewModelImpl(repo, categoryId)
//
//    @get:Rule
//    val instantTaskExecutorRule = InstantTaskExecutorRule()
////    @get:Rule
////    val coroutinesDispatcherRule = CoroutineDispatcherRule()
//
//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun getCategoryListTest() {
//        runTest {
//            assertTrue(viewModel.list.size == repo.getNewsList(categoryId, 0).size)
//        }
//    }
}