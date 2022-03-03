package com.example.newsproject.data

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class NewsRemoteDataSourceImpl(
    private val retrofitService: RetrofitService,
    private val dispatcher: CoroutineDispatcher
) : NewsRemoteDataSource {
    private val TAG = "MyNewsRemoteDataSource"

    override suspend fun getCategoryList(): Flow<List<Category>> {
        Log.d(TAG, "retrofitService.getCategoryList called")
        return flow {
            retrofitService.getCategoryList().also {
                if (it.code == 0)
                    emit(it.list)
                else
                    throw Throwable(it.message)
            }
        }.flowOn(dispatcher)
    }

    override suspend fun getNewsList(
        categoryId: Long,
        page: Int
    ): Flow<List<News>> {
        Log.d(TAG, "retrofitService.getNewsList called with categoryId = $categoryId, page = $page")
        return flow {
            retrofitService.getNewsList(categoryId, page).also {
                if (it.code == 0)
                    emit(it.list)
                else
                    throw Throwable(it.message)
            }
        }.flowOn(dispatcher)
    }

    override suspend fun getNews(
        newsId: Long
    ): Flow<News> {
        Log.d(TAG, "retrofitService.getNews called with newsId = $newsId")
        return flow {
            retrofitService.getNews(newsId).also {
                if (it.code == 0)
                    emit(it.news)
                else
                    throw Throwable(it.message)
            }
        }.flowOn(dispatcher)
    }
}