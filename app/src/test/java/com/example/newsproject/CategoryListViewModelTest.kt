package com.example.newsproject

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.newsproject.data.Category
import com.example.newsproject.data.NewsRepository
import com.example.newsproject.ui.categoryList.CategoryListViewModelImpl
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.robolectric.annotation.Config


@RunWith(AndroidJUnit4::class)
class CategoryListViewModelTest {

    @Mock
    private var repo: NewsRepository = NewsRepositoryFake()
    private lateinit var viewModel: CategoryListViewModelImpl

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun before() {
        MockitoAnnotations.openMocks(this)
        viewModel = CategoryListViewModelImpl(repo)
    }

    @Test
    fun verifyGetCategoryListCalled() {
        runBlocking {
            verify(repo).getCategoryList()
        }
    }

    private val vm: CategoryListViewModelImpl =
        CategoryListViewModelImpl(NewsRepositoryFake())
    @Test
    fun getCategoryListTest() {
        assertTrue(vm.list.value.isNotEmpty())
    }
}
