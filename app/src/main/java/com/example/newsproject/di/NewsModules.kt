package com.example.newsproject.di

import android.util.Log
import com.example.newsproject.data.NewsRemoteDataSource
import com.example.newsproject.data.NewsRemoteDataSourceImpl
import com.example.newsproject.data.NewsRepository
import com.example.newsproject.data.NewsRepositoryImpl
import com.example.newsproject.ui.categoryList.CategoryListViewModel
import com.example.newsproject.ui.categoryList.CategoryListViewModelImpl
import com.example.newsproject.ui.news.NewsViewModel
import com.example.newsproject.ui.news.NewsViewModelImpl
import com.example.newsproject.ui.newsList.NewsListViewModel
import com.example.newsproject.ui.newsList.NewsListViewModelImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppProvideModule {
    @Singleton
    @Provides
    fun provideNewsRepository(
        remoteDS: NewsRemoteDataSource
    ): NewsRepository = NewsRepositoryImpl(remoteDS, Default)

    @Singleton
    @Provides
    fun provideNewsRemoteDataSource(
        client: HttpClient
    ): NewsRemoteDataSource = NewsRemoteDataSourceImpl(client, IO)

    @Provides
    fun provideHttpClient(httpClientEngine: HttpClientEngine) = HttpClient(httpClientEngine) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                kotlinx.serialization.json.Json {
                    isLenient = true
                    ignoreUnknownKeys = true }
            )
        }
        install(DefaultRequest) {
            header("Accept", "application/json")
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.v("MyLogger_Ktor", message)
                }
            }
            level = LogLevel.ALL
        }
    }

    //creating engine for android platform
    @Provides
    fun provideHttpClientEngine(): HttpClientEngine = Android.create()
}


@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelProvideModule {
    @Binds
    abstract fun bindCategoryListViewModelImpl(
        impl: CategoryListViewModelImpl
    ): CategoryListViewModel

    @Binds
    abstract fun bindNewsListViewModelImpl(
        impl: NewsListViewModelImpl
    ): NewsListViewModel

    @Binds
    abstract fun bindNewsViewModelImpl(
        impl: NewsViewModelImpl
    ): NewsViewModel

}