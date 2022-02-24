package com.example.newsproject.data

import android.util.Log
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.response.*
import io.ktor.client.statement.*
import javax.inject.Inject

class NewsRemoteDataSourceImpl @Inject constructor(
    private val client: HttpClient
) :
    NewsRemoteDataSource {
    private val BASE_URL = "http://testtask.sebbia.com/v1/news/"
    private val TAG = "MyNewsRemoteDataSource"


    override suspend fun getCategoryList(): ApiCategoryList {
        Log.d(TAG, "getCategoryList called")
        return client.get<HttpStatement>("${BASE_URL}categories").body<ApiCategoryList>()
    }


    override suspend fun getNewsList(
        categoryId: Long,
        page: Int
    ): ApiNewsList {
        Log.d(TAG, "getNewsList called with categoryId = $categoryId, page = $page")
        return client.get("${BASE_URL}categories/${categoryId}/news") {
            parameter("page", page)
        }
    }

    override suspend fun getNews(
        newsId: Long
    ): ApiNews {
        Log.d(TAG, "getNews called with newsId = $newsId")
        return client.get("${BASE_URL}details") {
            parameter("id", newsId)
        }
    }

}