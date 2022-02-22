package com.example.newsproject.ui.categoryList

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
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

@Composable
fun CategoryListScreen(
    viewModel: CategoryListViewModel
) {
    // Livedata to State
    val items: List<Category> by viewModel.list.observeAsState(listOf())
    CategoryListList(
        list = items,
        onItemClick = { viewModel.onItemClicked(it.id) } //TODO Replace with navigate
    )
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
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(list) { category ->
            CategoryListItem(category, onItemClick)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategoryListItem(category: Category, onClick: (category: Category) -> Unit) {
    Card(
        shape = RoundedCornerShape(5.dp),
        backgroundColor = MaterialTheme.colors.surface, //TODO check mb redo
        onClick = { onClick(category) },
        modifier = Modifier
            .height(85.dp)
    ) {
        Text(
            text = category.name,
            style = TextStyle(fontSize = 20.sp),
            maxLines = 2,
            modifier = Modifier
                .padding(16.dp)
                .wrapContentHeight(Alignment.CenterVertically)
        )
    }
}