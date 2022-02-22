package com.example.newsproject.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import com.example.newsproject.ui.categoryList.CategoryListScreen
import com.example.newsproject.ui.newsList.NewsListScreen

sealed class Screen(val title: String) {
    object CategoryList : Screen("CategoryList")
    object NewsList : Screen("NewsList")
    object News : Screen("News")
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen() {
    //val navController = rememberNavController()

//    NewsAppThene {
//        Scaffold {
//
//            NavHost(navController, startDestination = Screen.PersonList.title) {
//                composable(
//                    route = Screen.CategoryList.title,
////                    exitTransition = {
////                        slideOutHorizontally() +
////                                fadeOut(animationSpec = tween(1000))
////                    },
////                    popEnterTransition = {
////                        slideInHorizontally()
////                    }
//                ) {
//                    //CategoryListScreen()
//                }
//                composable(
//                    route = Screen.NewsList.title,
////                    enterTransition = {
////                        slideInHorizontally() +
////                                fadeIn(animationSpec = tween(1000))
////                    },
////                    popExitTransition = {
////                        slideOutHorizontally()
////                    }
//                ) {
//                    //NewsListScreen()
//                }
//                composable(Screen.News.title) {
//                    ISSPositionScreen()
//                }
//            }
//        }
//    }
}