package com.example.newsproject.data;

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

class NewsRepositoryImpl(
    private val remoteDS: NewsRemoteDataSource,
    private val dispatcher: CoroutineDispatcher
) : NewsRepository {
    private val TAG = "MyNewsRepository"

    private val cacheMutex = Mutex()
    private var cache: NewsCache = NewsCache()

    override suspend fun getCategoryList(): Flow<List<Category>> {
        Log.d(TAG, "getCategoryList was called")
        return if (cache.categoryList.isEmpty())
            remoteDS.getCategoryList().onEach { list ->
                cacheMutex.withLock {
                    cache.categoryList = list
                }
            }.flowOn(dispatcher)
        else {
            flow {
                emit(cache.categoryList)
            }
        }
    }

    override suspend fun getNewsList(
        categoryId: Long,
        page: Int
    ): Flow<List<News>> {
        Log.d(TAG, "getNewsList was called")
        return if (cache.containsNewsPage(categoryId, page))
            flow {
                emit(cache.getNewsList(categoryId, page))
            }
        else
            remoteDS.getNewsList(categoryId, page).onEach { list ->
                cacheMutex.withLock {
                    cache.addNewsPage(categoryId, page, list)
                }
            }.flowOn(dispatcher)
    }

    override suspend fun getNewsFromCache(
        newsId: Long
    ): Flow<News> {
        Log.d(TAG, "getNewsFromCache was called")
        return flow {
            emit(cache.getNews(newsId))
        }
    }

    override suspend fun getNews(
        newsId: Long
    ): Flow<News> {
        Log.d(TAG, "getNews was called")
        val currentNews = cache.getNews(newsId)
        return if (currentNews.state)
            flow {
                emit(currentNews)
            }
        else
            remoteDS.getNews(newsId).onEach { news ->
                cacheMutex.withLock {
                    cache.setFullDes(news.id, news.fullDescription)
                }
            }.flowOn(dispatcher)
    }
}
