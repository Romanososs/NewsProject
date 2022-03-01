package com.example.newsproject.data;

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val remoteDS: NewsRemoteDataSource,
    private val localDS: NewsLocalDataSource,
    private val dispatcher: CoroutineDispatcher
) : NewsRepository {
    private val TAG = "MyNewsRepository"

    //private val cacheMutex = Mutex()
    //private var cache: NewsCache = NewsCache()

    override suspend fun getCategoryList(): List<Category> {
        Log.d(TAG, "getCategoryList was called")
        return localDS.getCategoryList().ifEmpty {
            withContext(dispatcher) {
                remoteDS.getCategoryList().also {
                    if (it.code == 0) {
                        localDS.setCategoryList(it.list)
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
        val cache = localDS.getNewsList(categoryId, page)
        val from = DateTimeFormatter.ISO_DATE_TIME
        val to = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
        return cache.ifEmpty {
            withContext(dispatcher) {
                remoteDS.getNewsList(categoryId, page).also {
                    if (it.code == 0) {
                        it.list.forEach{ news ->
                            news.date =
                                to.format(LocalDateTime.parse(news.date, from))
                        }
                        localDS.setNewsList(categoryId, page, it.list)
                    } else {
                        throw Throwable(it.message)
                    }
                }.list

            }
        }
    }

    /**
     * Get News from local data source
     */
    override suspend fun getQuickNews(
        newsId: Long
    ): News {
        Log.d(TAG, "getQuickNews was called")
        return localDS.getNews(newsId)
    }


    override suspend fun getNews(
        newsId: Long
    ): News {
        Log.d(TAG, "getNews was called")
        val currentNews = localDS.getNews(newsId)
        return if (currentNews.fullDescription != null) {
            currentNews
        } else {
            withContext(dispatcher) {
                try {
                    remoteDS.getNews(newsId).also {
                        if (it.code == 0) {
                            localDS.updateNews(it.news)
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
