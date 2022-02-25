package com.example.newsproject.data;

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val remoteDS: NewsRemoteDataSource,
    private val dispatcher: CoroutineDispatcher
) : NewsRepository {
    private val TAG = "MyNewsRepository"

    private val cacheMutex = Mutex()
    private var cache: NewsCache = NewsCache()

    override suspend fun getCategoryList(): List<Category> {
        Log.d(TAG, "getCategoryList was called")
        return cache.categoryList.ifEmpty {
            withContext(dispatcher) {
                remoteDS.getCategoryList().also {
                    if (it.code == 0) {
                        cacheMutex.withLock {
                            cache.categoryList = it.list
                        }
                    } else {
                        throw Throwable(it.message)
                    }
                }.list
            }
        }
    }

    override suspend fun getNewsList(
        categoryId: Long,
        page: Int
    ): List<News> {
        Log.d(TAG, "getNewsList was called")
        return if (cache.containsNewsPage(categoryId, page)) {
            cache.getNewsList(categoryId, page)
        } else {
            withContext(dispatcher) {
                remoteDS.getNewsList(categoryId, page).also {
                    if (it.code == 0) {
                        cacheMutex.withLock {
                            cache.addNewsPage(categoryId, page, it.list)
                        }
                    } else {
                        throw Throwable(it.message)
                    }
                }.list
            }
        }
    }

    override suspend fun getQuickNews(
        newsId: Long
    ): News {
        Log.d(TAG, "getQuickNews was called")
        return cache.getNews(newsId)
    }

    override suspend fun getNews(
        newsId: Long
    ): News {
        Log.d(TAG, "getNews was called")
        val currentNews = cache.getNews(newsId)
        return if (currentNews.state) {
            currentNews
        } else {
            withContext(dispatcher) {
                try {
                    remoteDS.getNews(newsId).also {
                        if (it.code == 0) {
                            cacheMutex.withLock {
                                cache.setNews(it.news, true)
                            }
                        } else {
                            throw Throwable(it.message)
                        }
                    }.news
                } catch (t: Throwable) {
                    currentNews
                }

            }
        }
    }
}
