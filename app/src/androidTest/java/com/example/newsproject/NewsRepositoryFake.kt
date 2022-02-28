package com.example.newsproject

import com.example.newsproject.data.Category
import com.example.newsproject.data.News
import com.example.newsproject.data.NewsRepository
import java.util.*

class NewsRepositoryFake : NewsRepository {

    override suspend fun getCategoryList() =
        listOf(
            Category(0, "Category 1"),
            Category(1, "Category 2"),
            Category(2, "Category 3")
        )

    override suspend fun getNewsList(categoryId: Long, page: Int) =
        listOf(
            News(0, "News 1", Date(0*1000), "Short Des1"),
            News(1, "News 2", Date(100*1000), "Short Des2"),
            News(2, "News 3", Date(200*1000), "Short Des3")
        )



    override suspend fun getQuickNews(newsId: Long) =
        News(newsId, "News 1", shortDescription = "Short Des")


    override suspend fun getNews(newsId: Long) =
        News(
            newsId, "News 1",
            shortDescription = "Short Des",
            fullDescription = "Full Des",
            state = true
        )
}