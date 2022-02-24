package com.example.newsproject.ui.news

import android.webkit.WebView
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.newsproject.data.News
import com.example.newsproject.ui.screenState.FailedScreen
import com.example.newsproject.ui.screenState.LoadingScreen
import com.example.newsproject.ui.screenState.ScreenState
import com.example.newsproject.ui.theme.Black
import com.example.newsproject.ui.theme.Grey700
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun NewsScreen(newsId: Long) {
    val viewModel: NewsViewModel = getViewModel<NewsViewModelImpl> { parametersOf(newsId) }
    // Livedata to State
    val news: News by viewModel.news.observeAsState(News())
    val state: ScreenState by viewModel.state.observeAsState(ScreenState.IsLoading)
    when (state)
    {
        ScreenState.IsLoading -> LoadingScreen()
        ScreenState.IsReady -> NewsLayout(news = news)
        else -> FailedScreen(viewModel.errorMessage.value ?: "")
    }

}

@Composable
fun NewsLayout(news: News) {
    Column {
        Text(
            text = news.title,
            fontSize = 20.sp,
            color = Black,
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 16.dp
                )
        )
        Text(
            text = news.shortDescription,
            fontSize = 16.sp,
            color = Grey700,
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 8.dp
                )
        )
        AndroidView(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxHeight(),
            factory = {
                WebView(it).apply {
                    loadData(
                        news.fullDescription,
                        "text/html; charset=utf-8",
                        "UTF-8"
                    )
                }
            })
    }
}

