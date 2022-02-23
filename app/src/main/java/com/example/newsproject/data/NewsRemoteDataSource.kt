package com.example.newsproject.data

interface NewsRemoteDataSource {

    suspend fun getCategoryList(): ApiCategoryList

    suspend fun getNewsList(
        categoryId: Long,
        page: Int
    ): ApiNewsList

    suspend fun getNews(
        newsId: Long
    ): ApiNews
}