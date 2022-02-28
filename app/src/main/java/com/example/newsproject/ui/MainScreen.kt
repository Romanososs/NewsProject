package com.example.newsproject.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
//import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import androidx.navigation.navArgument
import com.example.newsproject.ui.categoryList.CategoryListScreen
import com.example.newsproject.ui.news.NewsScreen
import com.example.newsproject.ui.newsList.NewsListScreen
import com.example.newsproject.ui.theme.NewsAppTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost

const val durationMil: Int = 500

sealed class Screen(val title: String) {
    object CategoryList : Screen("CategoryList")
    object NewsList : Screen("NewsList")
    object News : Screen("News")
}


@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val screenWidth =
        LocalConfiguration.current.screenWidthDp * LocalConfiguration.current.densityDpi
    NewsAppTheme {
        Scaffold {
            NavHost(navController, startDestination = Screen.CategoryList.title) {
                composable(
                    route = Screen.CategoryList.title,
                ) {
                    CategoryListScreen { id ->
                        navController.navigate("${Screen.NewsList.title}/${id}")
                    }
                }
                composable(
                    route = "${Screen.NewsList.title}/{categoryId}",
                    arguments = listOf(navArgument("categoryId") { type = NavType.LongType }),
                ) {
                    NewsListScreen(
                        categoryId = it.arguments?.getLong("categoryId") ?: -1
                    ) { id ->
                        navController.navigate("${Screen.News.title}/${id}")
                    }
                }
                composable(
                    route = "${Screen.News.title}/{newsId}",
                    arguments = listOf(navArgument("newsId") { type = NavType.LongType }),
                ) {
                    NewsScreen(
                        newsId = it.arguments?.getLong("newsId") ?: -1
                    )
                }
            }
        }
    }
}

//                    enterTransition = {
//                        slideInHorizontally(
//                            initialOffsetX = { screenWidth },
//                            animationSpec = tween(durationMil)
//                        )
//                    },
//                    exitTransition = {
//                        slideOutHorizontally(
//                            targetOffsetX = { -screenWidth },
//                            animationSpec = tween(durationMil)
//                        )
//                    },
//                    popEnterTransition = {
//                        slideInHorizontally(
//                            initialOffsetX = { -screenWidth },
//                            animationSpec = tween(durationMil)
//                        )
//                    },
//                    popExitTransition = {
//                        slideOutHorizontally(
//                            targetOffsetX = { screenWidth },
//                            animationSpec = tween(durationMil)
//                        )
//                    }