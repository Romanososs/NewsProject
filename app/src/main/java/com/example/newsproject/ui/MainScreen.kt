package com.example.newsproject.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newsproject.ui.categoryList.CategoryListScreen
import com.example.newsproject.ui.news.NewsScreen
import com.example.newsproject.ui.newsList.NewsListScreen
import com.example.newsproject.ui.theme.NewsAppTheme

sealed class Screen(val title: String) {
    object CategoryList : Screen("CategoryList")
    object NewsList : Screen("NewsList")
    object News : Screen("News")
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    NewsAppTheme {
        Scaffold {
            NavHost(navController, startDestination = Screen.CategoryList.title) {
                composable(
                    route = Screen.CategoryList.title,
//                    exitTransition = {
//                        slideOutHorizontally() +
//                                fadeOut(animationSpec = tween(1000))
//                    },
//                    popEnterTransition = {
//                        slideInHorizontally()
//                    }
                ) {
                    CategoryListScreen { id ->
                        navController.navigate("${Screen.NewsList.title}/${id}")
                    }
                }
                composable(
                    route = "${Screen.NewsList.title}/{categoryId}",
                    arguments = listOf(navArgument("categoryId") { type = NavType.LongType })
//                    enterTransition = {
//                        slideInHorizontally() +
//                                fadeIn(animationSpec = tween(1000))
//                    },
//                    popExitTransition = {
//                        slideOutHorizontally()
//                    }
                ) {
                    NewsListScreen { id ->
                        navController.navigate("${Screen.News.title}/${id}")
                    }
                }
                composable(
                    route = "${Screen.News.title}/{newsId}",
                    arguments = listOf(navArgument("newsId") { type = NavType.LongType })
                ) {
                    NewsScreen()
                }
            }
        }
    }
}