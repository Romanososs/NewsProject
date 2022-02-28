package com.example.newsproject

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.newsproject.ui.categoryList.CategoryListScreen
import com.example.newsproject.ui.categoryList.CategoryListViewModelImpl
import com.example.newsproject.ui.news.NewsScreen
import com.example.newsproject.ui.news.NewsViewModelImpl
import com.example.newsproject.ui.newsList.DateTimeFormat
import com.example.newsproject.ui.newsList.NewsListScreen
import com.example.newsproject.ui.newsList.NewsListViewModelImpl
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import java.text.SimpleDateFormat

class NewsAppUITest {
    @get: Rule
    val composeTestRule = createComposeRule()

    private val repo = NewsRepositoryFake()

    @Test
    fun testCategoryListScreen() {
        val categoryListVM = CategoryListViewModelImpl(repo)
        composeTestRule.setContent {
            CategoryListScreen(categoryListVM, navigateToNewsList = {})
        }
        runBlocking{
            val list = repo.getCategoryList()
            composeTestRule
                .onAllNodes(hasClickAction())
                .assertCountEquals(list.size)
            list.forEach { category ->
                composeTestRule.onNodeWithText(category.name).assertExists()
            }
        }
    }

    @Test
    fun testNewsListScreen(){
        val categoryId = 0L
        val newsListVM = NewsListViewModelImpl(repo, categoryId)
        composeTestRule.setContent {
            NewsListScreen(newsListVM, categoryId, navigateToNews = {})
        }
        runBlocking{
            val list = repo.getNewsList(categoryId, 0)
            composeTestRule
                .onAllNodes(hasClickAction())
                .assertCountEquals(list.size)
            list.forEach { news ->
                composeTestRule.onNodeWithText(news.title).assertExists()
                composeTestRule.onNodeWithText(SimpleDateFormat(DateTimeFormat).format(news.date)).assertExists()
                composeTestRule.onNodeWithText(news.shortDescription).assertExists()
            }
        }
    }

    @Test
    fun testNewsScreen(){
        val newsId = 0L
        val newsVM = NewsViewModelImpl(repo, newsId)
        composeTestRule.setContent {
            NewsScreen(newsVM,newsId)
        }
        runBlocking{
            val news = repo.getNews(newsId)
            composeTestRule.onNodeWithText(news.title).assertExists()
            composeTestRule.onNodeWithText(news.shortDescription).assertExists()
            composeTestRule.onNodeWithTag("NewsWebView").assertExists()
        }
    }

//    @Test
//    fun testAppNavigation(){
//        composeTestRule.setContent {
//            MainScreen()
//        }
//        composeTestRule.onNodeWithText("Category 1").performClick()
//        composeTestRule.onNodeWithText("News 1").assertExists()
//
//        composeTestRule.onNodeWithText("News 1").performClick()
//        composeTestRule.onNodeWithTag("NewsWebView").assertExists()
//    }
}