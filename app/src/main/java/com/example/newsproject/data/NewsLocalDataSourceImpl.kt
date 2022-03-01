package com.example.newsproject.data

import com.example.newsproject.Database
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsLocalDataSourceImpl @Inject constructor(
    private val db: Database,
    private val dispatcher: CoroutineDispatcher
) : NewsLocalDataSource {
    private val query = db.newsQueries

    override suspend fun getCategoryList(): List<Category> =
        withContext(dispatcher) {
            //query.selectAllCategory().executeAsList().map { Category(it) }
            query.selectAllCategory { id, name -> Category(id, name) }.executeAsList()
        }

    override suspend fun setCategoryList(list: List<Category>) {
        withContext(dispatcher) {
            query.deleteAllCategory()
            list.forEach {
                query.insetIntoCategory(it.id, it.name)
            }
        }
    }

    override suspend fun getNewsList(categoryId: Long, page: Int): List<News> =
        withContext(dispatcher) {
            query.selectNewsPage(categoryId, page.toLong())
            { id, _, title, date, shortDescription, fullDescription, _ ->
                News(id, title, date, shortDescription, fullDescription)
            }.executeAsList()
//            query.selectNewsPage(categoryId, page.toLong())
//                .executeAsList()
//                .map { News(it) }
        }

    override suspend fun setNewsList(categoryId: Long, page: Int, list: List<News>) {
        withContext(dispatcher) {
            list.forEach {
                query.insetIntoNews(
                    it.id,
                    page.toLong(),
                    it.title,
                    it.date,
                    it.shortDescription,
                    it.fullDescription,
                    categoryId
                )
            }
        }
    }

    override suspend fun getNews(newsId: Long): News =
        withContext(dispatcher) {
            query.selectNews(newsId) { id, _, title, date, shortDescription, fullDescription, _ ->
                News(id, title, date, shortDescription, fullDescription)
            }.executeAsOne()
        }

    override suspend fun updateNews(news: News) {
        withContext(dispatcher) {
            query.updateNews(news.fullDescription, news.id)
        }
    }

}