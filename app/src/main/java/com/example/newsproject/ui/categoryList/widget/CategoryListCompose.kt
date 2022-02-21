package com.example.newsproject.ui.categoryList.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsproject.data.Category
import com.example.newsproject.ui.categoryList.CategoryListViewModel

@Composable
fun CategoryListScreen(
    viewModel: CategoryListViewModel
) {
    val items: List<Category> by viewModel.list.observeAsState(listOf())
    CategoryListList {

    }
}

@Composable
fun CategoryListList(
    paddingValues: PaddingValues = PaddingValues(),
    itemClick: (category: Category) -> Unit
) {
    val state = viewModel.state

    Row(
//        modifier = Modifier
//            .fillMaxWidth()
    ) {
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.fillMaxWidth()
        ) {
            items(peopleState.value) { category ->
                CategoryListItem(category, personSelected)
            }
        }
    }
}

@Composable
fun CategoryListItem(category: Category, click: (category: Category) -> Unit) {

    Card(
        shape = RoundedCornerShape(5.dp),
        backgroundColor = MaterialTheme.colors.surface, //TODO check
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { click(category) })//TODO impl
            .padding(16.dp)
    ) {
        Text(
            text = category.name,
            style = TextStyle(fontSize = 20.sp)
        )
    }
}