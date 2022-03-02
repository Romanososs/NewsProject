package com.example.newsproject

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.newsproject.data.NewsRepository
import com.example.newsproject.ui.news.NewsViewModelImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NewsViewModelTest {

    private val repo: NewsRepository = NewsRepositoryFake()
    private val newsId = 0L
    private val viewModel: NewsViewModelImpl = NewsViewModelImpl(repo, newsId)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getCategoryListTest() {
        runTest {
            Assert.assertTrue(viewModel.news.value.fullDescription == repo.getNews(newsId).fullDescription)
        }
    }
}