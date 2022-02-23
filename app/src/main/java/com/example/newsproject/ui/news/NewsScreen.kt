package com.example.newsproject.ui.news

import android.annotation.SuppressLint
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
import com.example.newsproject.ui.theme.Black
import com.example.newsproject.ui.theme.Grey700
import org.koin.androidx.compose.getViewModel

@Composable
fun NewsScreen() {
    val viewModel: NewsViewModel = getViewModel<NewsViewModelImpl>()
    // Livedata to State
    val news: News by viewModel.news.observeAsState(News())
    NewsLayout(news = news)
}

@SuppressLint("SimpleDateFormat")
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
            modifier = Modifier.padding(top = 8.dp),
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

