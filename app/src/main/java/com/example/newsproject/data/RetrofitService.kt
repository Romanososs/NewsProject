package com.example.newsproject.data

import retrofit2.http.*

interface RetrofitService {
    @GET("categories")
    suspend fun getCategoryList(): ApiCategoryList

    @GET("categories/{id}/news")
    suspend fun getNewsList(
        @Path("id") id: Long,
        @Query("page") page: Int
    ): ApiNewsList

    @GET("details")
    suspend fun getNews(@Query("id") id: Long): ApiNews
}