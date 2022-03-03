package com.example.newsproject.data

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface NewsRemoteDataSource {

    suspend fun getCategoryList(): Flow<List<Category>>

    suspend fun getNewsList(
        categoryId: Long,
        page: Int
    ): Flow<List<News>>

    suspend fun getNews(
        newsId: Long
    ): Flow<News>
}