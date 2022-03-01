package com.example.newsproject.ui.categoryList

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.elevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsproject.data.Category
import com.example.newsproject.ui.screenState.FailedScreen
import com.example.newsproject.ui.screenState.LoadingScreen
import com.example.newsproject.ui.screenState.ScreenState
import org.koin.androidx.compose.getStateViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.viewModel

@Composable
fun CategoryListScreen(
    viewModel: CategoryListViewModel = getViewModel<CategoryListViewModelImpl>(),
    navigateToNewsList: (id: Long) -> Unit
) {
    // Livedata to State
    val items: List<Category> by viewModel.list
    val state: ScreenState by viewModel.state
    when (state) {
        ScreenState.IsLoading -> LoadingScreen()
        ScreenState.IsReady -> CategoryListList(
            list = items,
            onItemClick = {
                navigateToNewsList(it.id)
            }
        )
        else -> FailedScreen(viewModel.errorMessage ?: "")
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryListList(
    list: List<Category>,
    onItemClick: (category: Category) -> Unit
) {
    LazyVerticalGrid(
        cells = GridCells.Adaptive(150.dp), //mb put this values into res or something
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(list) { category ->
            CategoryListItem(category, onItemClick)
        }
    }
}

@Composable
fun CategoryListItem(category: Category, onClick: (category: Category) -> Unit) {
    Button(
        onClick = { onClick(category) },
        contentPadding = PaddingValues(16.dp),
        shape = RoundedCornerShape(5.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
        elevation = elevation(defaultElevation = 10.dp, pressedElevation = 10.dp),
        modifier = Modifier
            .height(85.dp)
    ) {
        Text(
            text = category.name,
            style = TextStyle(fontSize = 20.sp),
            maxLines = 2,
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight(Alignment.CenterVertically)
        )
    }
}