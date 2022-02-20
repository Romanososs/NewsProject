package com.example.newsproject.data

interface NewsRepository {
    suspend fun getCategoryList(): List<Category>

    suspend fun getNewsList(
        categoryId: Long,
        page: Int
    ): List<News>

    suspend fun getQuickNews(
        newsId: Long
    ): News

    suspend fun getNews(
        newsId: Long
    ): News
}