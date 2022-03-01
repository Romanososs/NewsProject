package com.example.newsproject.data

interface NewsLocalDataSource {
    suspend fun getCategoryList(): List<Category>
    suspend fun setCategoryList(list: List<Category>)

    suspend fun getNewsList(
        categoryId: Long,
        page: Int
    ): List<News>
    suspend fun setNewsList(
        categoryId: Long,
        page: Int,
        list: List<News>
    )

    suspend fun getNews(
        newsId: Long
    ): News

    /**
     * For setting fullDescription only
     */
    suspend fun updateNews(news: News)
}