package com.example.newsproject.data

import java.time.Instant
import java.util.Date

//API Models
data class ApiCategoryList(
    val code: Int = -1,
    val message: String = "",
    val list: List<Category> = emptyList()
)

data class ApiNewsList(
    val code: Int = -1,
    val message: String = "",
    val list: MutableList<News> = mutableListOf()
)

data class ApiNews(
    val code: Int = -1,
    val message: String = "",
    val news: News = News()
)

//Data Model
data class Category(
    val id: Long = -1,
    val name: String = ""
)

data class News(
    val id: Long = -1,
    val title: String = "",
    var date: String = "",
    val shortDescription: String = "",
    val fullDescription: String? = null
)