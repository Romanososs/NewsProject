package com.example.newsproject.di

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
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
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
    ): NewsRemoteDataSource = NewsRemoteDataSourceImpl(IO)
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