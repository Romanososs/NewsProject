package com.example.newsproject.data

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class NewsRemoteDataSourceImpl @Inject constructor(
    private val dispatcher: CoroutineDispatcher
) :
    NewsRemoteDataSource {
    private val BASE_URL = "http://testtask.sebbia.com/v1/news/"
    private val TAG = "MyNewsRemoteDataSource"

    private val retrofitService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RetrofitService::class.java)

    override suspend fun getCategoryList() =
        withContext(dispatcher) {
            Log.d(TAG, "retrofitService.getCategoryList called")
            retrofitService.getCategoryList()
        }


    override suspend fun getNewsList(
        categoryId: Long,
        page: Int
    ) = withContext(dispatcher) {
        Log.d(TAG, "retrofitService.getNewsList called with categoryId = $categoryId, page = $page")
        retrofitService.getNewsList(categoryId, page)
    }

    override suspend fun getNews(
        newsId: Long
    ) = withContext(dispatcher) {
        Log.d(TAG, "retrofitService.getNews called with newsId = $newsId")
        retrofitService.getNews(newsId)
    }

}