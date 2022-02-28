package com.example.newsproject.ui.newsList

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsproject.data.News
import com.example.newsproject.ui.screenState.FailedScreen
import com.example.newsproject.ui.screenState.LoadingScreen
import com.example.newsproject.ui.screenState.ScreenState
import com.example.newsproject.ui.theme.Black
import com.example.newsproject.ui.theme.Grey700
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf
import java.text.SimpleDateFormat

const val DateTimeFormat = "dd.MM.yyyy HH:mm"

@Composable
fun NewsListScreen(
    viewModel: NewsListViewModel = getViewModel<NewsListViewModelImpl> { parametersOf(categoryId) },
    categoryId: Long,
    navigateToNews: (id: Long) -> Unit
) {
    // Livedata to State
    val items: List<News> by viewModel.list.observeAsState(listOf())
    val state: ScreenState by viewModel.state.observeAsState(ScreenState.IsLoading)
    when (state) {
        ScreenState.IsLoading -> LoadingScreen()
        ScreenState.IsReady -> NewsListList(
            list = items,
            onItemClick = {
                navigateToNews(it.id)
            }
        )
        else -> FailedScreen(viewModel.errorMessage.value ?: "")
    }
}

@Composable
fun NewsListList(
    list: List<News>,
    onItemClick: (item: News) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(list) { news ->
            NewsListItem(news, onItemClick)
        }
    }
}

@SuppressLint("SimpleDateFormat")
@Composable
fun NewsListItem(news: News, onClick: (news: News) -> Unit) {
    Button(
        onClick = { onClick(news) },
        contentPadding = PaddingValues(16.dp),
        shape = RoundedCornerShape(5.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
        elevation = ButtonDefaults.elevation(defaultElevation = 10.dp, pressedElevation = 10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Text(
                //TODO what to do with overFlow
                text = news.title,
                style = TextStyle(fontSize = 20.sp),
                color = Black,
                maxLines = 2,
                //overFlow = TextOverflow.Ellipsis
            )
            Text(
                text = SimpleDateFormat(DateTimeFormat).format(news.date),
                style = TextStyle(fontSize = 14.sp),
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
                style = TextStyle(fontSize = 16.sp),
                color = Black,
                maxLines = 2
            )
        }
    }
}