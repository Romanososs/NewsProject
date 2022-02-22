package com.example.newsproject.ui.newsList

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsproject.data.Category
import com.example.newsproject.data.News
import com.example.newsproject.ui.categoryList.CategoryListViewModel
import java.text.SimpleDateFormat

@Composable
fun NewsListScreen(
    viewModel: NewsListViewModel
) {
    // Livedata to State
    val items: List<News> by viewModel.list.observeAsState(listOf())
    NewsListList(
        list = items,
        onItemClick = { viewModel.onItemClicked(it.id) } //TODO Replace with navigate
    )
}

@OptIn(ExperimentalFoundationApi::class)
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewsListItem(news: News, onClick: (news: News) -> Unit) {
    Card(
        shape = RoundedCornerShape(5.dp),
        backgroundColor = MaterialTheme.colors.surface, //TODO check mb redo
        onClick = { onClick(news) }//TODO add
    ) {
        Text(//TODO overFlow doesn't work
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
        Text(
            text = news.shortDescription,
            fontSize = 20.sp,
            maxLines = 2,
            modifier = Modifier
                .padding(16.dp)
                .wrapContentHeight(Alignment.CenterVertically)
        )
    }
}