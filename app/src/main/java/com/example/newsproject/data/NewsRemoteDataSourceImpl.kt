package com.example.newsproject.data

import android.util.Log
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.response.*
import io.ktor.client.statement.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsRemoteDataSourceImpl @Inject constructor(
    private val client: HttpClient,
    private val dispatcher: CoroutineDispatcher
) :
    NewsRemoteDataSource {
    private val BASE_URL = "http://testtask.sebbia.com/v1/news/"
    private val TAG = "MyNewsRemoteDataSource"


    override suspend fun getCategoryList(
    ): ApiCategoryList = withContext(dispatcher) {
        Log.d(TAG, "getCategoryList called")
        client.get("${BASE_URL}categories")
    }


    override suspend fun getNewsList(
        categoryId: Long,
        page: Int
    ): ApiNewsList = withContext(dispatcher) {
        Log.d(TAG, "getNewsList called with categoryId = $categoryId, page = $page")
        client.get("${BASE_URL}categories/${categoryId}/news") {
            parameter("page", page)
        }
    }

    override suspend fun getNews(
        newsId: Long
    ): ApiNews = withContext(dispatcher) {
        Log.d(TAG, "getNews called with newsId = $newsId")
        client.get("${BASE_URL}details") {
            parameter("id", newsId)
        }
    }

}