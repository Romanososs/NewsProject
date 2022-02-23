package com.example.newsproject.data

import java.time.Instant
import java.util.Date

//API Models
data class ApiCategoryList(
    val code: Int = -1,
    val message: String = "",
    val list: List<Category> = emptyList()
)

data class ApiNewsList(
    val code: Int = -1,
    val message: String = "",
    val list: MutableList<News> = mutableListOf()
)

data class ApiNews(
    val code: Int = -1,
    val message: String = "",
    val news: News = News()
)

//Cached Data
data class NewsCache(
    val newsPageList: MutableList<NewsPage> = mutableListOf(),
    var categoryList: List<Category> = listOf()
) {

    fun containsNewsPage(categoryId: Long, page: Int): Boolean {
        for (item in newsPageList)
            if (item.categoryId == categoryId && item.page == page) return true
        return false
    }

    fun addNewsPage(categoryId: Long, page: Int, list: MutableList<News>) {
        newsPageList.add(NewsPage(categoryId, page, list))
    }

    fun getNewsList(categoryId: Long, page: Int): List<News> {
        for (item in newsPageList)
            if (item.categoryId == categoryId && item.page == page) return item.newsList
        return listOf()
    }

    fun getNews(newsId: Long): News {
        for (page in newsPageList)
            for (news in page.newsList)
                if (news.id == newsId) return news
        return News()
    }

    fun setNews(news: News, state: Boolean = false) {
        for (page in newsPageList)
            for (i in page.newsList.indices)
                if (page.newsList[i].id == news.id) {
                    news.state = state
                    page.newsList[i] = news
                    return
                }
    }
}

data class NewsPage(
    val categoryId: Long = -1,
    val page: Int = -1,
    val newsList: MutableList<News> = mutableListOf()
)


//Data Model
data class Category(
    val id: Long = -1,
    val name: String = ""
)

data class News(
    val id: Long = -1,
    val title: String = "",
    val date: Date = Date.from(Instant.now()),
    val shortDescription: String = "",
    val fullDescription: String = "",
    //true - have fullDescription, false - fullDescription is empty str
    var state: Boolean = false
)