package com.example.newsproject.ui.news

import android.annotation.SuppressLint
import android.webkit.WebView
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.newsproject.data.News
import java.text.SimpleDateFormat

@Composable
fun NewsListScreen(
    viewModel: NewsViewModel
) {
    // Livedata to State
    val news: News by viewModel.news.observeAsState(News())
    NewsLayout(
        news = news
    )
}

@SuppressLint("SimpleDateFormat")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NewsLayout(
    news: News
) {
    Column {
        Text(
            text = news.title,
            style = TextStyle(fontSize = 20.sp),
            maxLines = 2,
            modifier = Modifier
                .padding(16.dp)
                .wrapContentHeight(Alignment.CenterVertically)
        )
        Text(
            text = SimpleDateFormat("dd.MM.yyyy HH:mm").format(news.date),
            fontSize = 20.sp,
            maxLines = 2,
            modifier = Modifier
                .padding(16.dp)
                .wrapContentHeight(Alignment.CenterVertically)
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

