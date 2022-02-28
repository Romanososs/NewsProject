package com.example.newsproject

import androidx.lifecycle.Observer
import com.example.newsproject.data.News
import com.example.newsproject.ui.newsList.NewsListViewModelImpl
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class NewsListViewModelTest {
    @Mock
    private val viewModel = NewsListViewModelImpl(NewsRepositoryFake(), 0)
    private val observer = Observer<List<News>> {}


    @Test
    fun getNewsListOnInitTest() {
        try {
            viewModel.list.observeForever(observer)
            val value = viewModel.list.value
            assertTrue(value?.isNotEmpty() ?: false)

        } finally {
            viewModel.list.removeObserver(observer)
        }
    }

}