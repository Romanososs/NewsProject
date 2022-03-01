package com.example.newsproject

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.newsproject.data.News
import com.example.newsproject.data.NewsRepository
import com.example.newsproject.ui.categoryList.CategoryListViewModelImpl
import com.example.newsproject.ui.newsList.NewsListViewModelImpl
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class NewsListViewModelTest {
    @Mock
    private var repo: NewsRepository = NewsRepositoryFake()
    private lateinit var viewModel: NewsListViewModelImpl
    private val categoryId: Long = 0

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val coroutinesDispatcherRule = CoroutineDispatcherRule()
    @Before
    fun before() {
        MockitoAnnotations.openMocks(this)
        viewModel = NewsListViewModelImpl(repo, categoryId)
    }

    @Test
    fun verifyGetCategoryListCalled() {
        runBlocking {
            verify(repo).getNewsList(categoryId, 0)
            viewModel.getNewPage()
            //verify(repo, times(2)).getNewsList()
        }
    }

    private val vm: NewsListViewModelImpl =
        NewsListViewModelImpl(NewsRepositoryFake(), categoryId)

    @Test
    fun getCategoryListTest() {
        runBlocking {
            assertTrue(vm.list.size == repo.getNewsList(categoryId, 0).size)
        }
    }
}