package com.example.newsproject.data

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class NewsRemoteDataSourceImpl:
    NewsRemoteDataSource{
    private val BASE_URL = "http://testtask.sebbia.com/v1/news/"
    private val TAG = "MyNewsRemoteDataSource"

    private val retrofitService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RetrofitService::class.java)

    override fun getCategoryList(callback: (Result<ApiCategoryList?>) -> Unit) {
        Log.d(TAG, "retrofitService.getCategoryList called")
        val call = retrofitService.getCategoryList()
        call.enqueue(object : Callback<ApiCategoryList?> {
            override fun onResponse(
                call: Call<ApiCategoryList?>,
                response: Response<ApiCategoryList?>
            ) {
                callback(
                    Result.success(
                        response.body()
                    )
                )
            }

            override fun onFailure(call: Call<ApiCategoryList?>, throwable: Throwable) {
                callback(
                    Result.failure(
                        throwable
                    )
                )
            }
        }
        )
    }


    override fun getNewsList(
        categoryId: Long,
        page: Int,
        callback: (Result<ApiNewsList?>) -> Unit
    ) {
        Log.d(TAG, "retrofitService.getNewsList called with categoryId = $categoryId, page = $page")
        val call = retrofitService.getNewsList(categoryId, page)
        call.enqueue(object : Callback<ApiNewsList?> {
            override fun onResponse(
                call: Call<ApiNewsList?>,
                response: Response<ApiNewsList?>
            ) {
                callback(
                    Result.success(
                        response.body()
                    )
                )
            }

            override fun onFailure(call: Call<ApiNewsList?>, throwable: Throwable) {
                callback(
                    Result.failure(
                        throwable
                    )
                )
            }
        }
        )
    }

    override fun getNews(
        newsId: Long,
        callback: (Result<ApiNews?>) -> Unit
    ) {
        Log.d(TAG, "retrofitService.getNews called with newsId = $newsId")
        val call = retrofitService.getNews(newsId)
        call.enqueue(object : Callback<ApiNews?> {
            override fun onResponse(
                call: Call<ApiNews?>,
                response: Response<ApiNews?>
            ) {
                callback(
                    Result.success(
                        response.body()
                    )
                )
            }

            override fun onFailure(call: Call<ApiNews?>, throwable: Throwable) {
                callback(
                    Result.failure(
                        throwable
                    )
                )
            }
        }
        )
    }

}