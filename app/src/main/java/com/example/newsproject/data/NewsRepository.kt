package com.example.newsproject.data

import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getCategoryList(): Flow<List<Category>>

    suspend fun getNewsList(
        categoryId: Long,
        page: Int
    ): Flow<List<News>>

    suspend fun getNewsFromCache(
        newsId: Long
    ): Flow<News>

    suspend fun getNews(
        newsId: Long
    ): Flow<News>
}