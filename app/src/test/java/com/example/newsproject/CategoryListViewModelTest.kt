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


@Config(manifest = Config.NONE)
@RunWith(AndroidJUnit4::class)
class CategoryListViewModelTest {
    @Mock
    private lateinit var repo: NewsRepository

    private lateinit var viewModel: CategoryListViewModelImpl
    private val observer = Observer<List<Category>> {}

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun before() {
        MockitoAnnotations.openMocks(this)
        runBlocking {
           // repo = Mockito.mock(NewsRepositoryFake.class, "repo")
            //when(repo.getCategoryList()).thenCallRealMethod()
        //doReturn()
            viewModel = spy(CategoryListViewModelImpl(repo))
           viewModel.list.observeForever(observer)
        }

    }

    @After
    fun after() {
        viewModel.list.removeObserver(observer)
    }

    @Test
    fun verifyGetCategoryListCalled() {
        runBlocking {
            verify(repo).getCategoryList()
        }
    }

    @Test
    fun getCategoryListTest() {
        val value = viewModel.list.value
        assertTrue(value?.isNotEmpty() ?: false)
    }

}