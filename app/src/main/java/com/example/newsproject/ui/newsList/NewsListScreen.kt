package com.example.newsproject.ui.newsList

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.newsproject.data.News
import com.example.newsproject.ui.Screen
import com.example.newsproject.ui.theme.Black
import com.example.newsproject.ui.theme.Grey700
import org.koin.androidx.compose.getStateViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.viewModel
import java.text.SimpleDateFormat

@ExperimentalMaterialApi
@Composable
fun NewsListScreen(navigateToNews:(id: Long) -> Unit) {
    val viewModel: NewsListViewModel = getViewModel<NewsListViewModelImpl>()
    // Livedata to State
    val items: List<News> by viewModel.list.observeAsState(listOf())
    NewsListList(
        list = items,
        onItemClick = {
            navigateToNews(it.id)
        }
    )
}

@ExperimentalMaterialApi
@Composable
fun NewsListList(
    list: List<News>,
    onItemClick: (item: News) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(list) { news ->
            NewsListItem(news, onItemClick)
        }
    }
}

@ExperimentalMaterialApi
@SuppressLint("SimpleDateFormat")
@Composable
fun NewsListItem(news: News, onClick: (news: News) -> Unit) {
    Card(
        shape = RoundedCornerShape(5.dp),
        backgroundColor = MaterialTheme.colors.surface, //TODO check mb redo
        onClick = { onClick(news) },//TODO add
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(//TODO overFlow doesn't work
                text = news.title,
                fontSize = 20.sp,
                color = Black,
                maxLines = 2
            )
            Text(
                text = SimpleDateFormat("dd.MM.yyyy HH:mm").format(news.date),
                fontSize = 14.sp,
                color = Grey700,
                modifier = Modifier
                    .padding(
                        start = 4.dp,
                        end = 4.dp,
                        top = 4.dp,
                        bottom = 8.dp
                    )
            )
            Text(
                text = news.shortDescription,
                fontSize = 16.sp,
                color = Black,
                maxLines = 2
            )
        }
    }
}