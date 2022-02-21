package com.example.newsproject.ui.categoryList.widget

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsproject.data.Category

//@Composable
//fun CategoryListScreen(paddingValues: PaddingValues = PaddingValues(),
//                     itemClick: (category: Category) -> Unit,
//                     peopleInSpaceViewModel: PeopleInSpaceViewModel = getViewModel()
//) {
//    val peopleState = peopleInSpaceViewModel.peopleInSpace.collectAsState()
//
//    Scaffold(
//        topBar = {
//            TopAppBar(title = { Text("HELP") })
//        }
//    ) {
//        LazyColumn(contentPadding = paddingValues, modifier = Modifier.testTag(PersonListTag)) {
//            items(peopleState.value) { category ->
//                CategoryListItem(category, personSelected)
//            }
//        }
//    }
//}

//@Composable
//fun CategoryListItem(category: Category, click: (category: Category) -> Unit) {
//
//    Row(modifier = Modifier
//        .fillMaxWidth()
//        .clickable(onClick = { click(category) })//TODO impl
//        .padding(16.dp), //used to be in res
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//
//        val personImageUrl = ca.personImageUrl ?: ""
//        if (personImageUrl.isNotEmpty()) {
//            Image(
//                painter = rememberImagePainter(personImageUrl),
//                modifier = Modifier.size(60.dp), contentDescription = person.name
//            )
//        } else {
//            Spacer(modifier = Modifier.size(60.dp))
//        }
//
//        Spacer(modifier = Modifier.size(12.dp))
//
//        Column {
//            Text(text = person.name, style = TextStyle(fontSize = 20.sp))
//            Text(text = person.craft, style = TextStyle(color = Color.DarkGray, fontSize = 14.sp))
//        }
//    }
//}