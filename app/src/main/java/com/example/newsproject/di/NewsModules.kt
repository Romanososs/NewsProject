package com.example.newsproject.di

import android.content.Context
import com.example.newsproject.Database
import com.example.newsproject.data.*
import com.example.newsproject.ui.categoryList.CategoryListViewModel
import com.example.newsproject.ui.categoryList.CategoryListViewModelImpl
import com.example.newsproject.ui.news.NewsViewModel
import com.example.newsproject.ui.news.NewsViewModelImpl
import com.example.newsproject.ui.newsList.NewsListViewModel
import com.example.newsproject.ui.newsList.NewsListViewModelImpl
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
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
        remoteDS: NewsRemoteDataSource,
        localDS: NewsLocalDataSource
    ): NewsRepository = NewsRepositoryImpl(remoteDS, localDS, Default)

    @Singleton
    @Provides
    fun provideNewsRemoteDataSource(
    ): NewsRemoteDataSource = NewsRemoteDataSourceImpl(IO)

    @Singleton
    @Provides
    fun provideNewsLocalDataSource(
        db: Database
    ): NewsLocalDataSource = NewsLocalDataSourceImpl(db, IO)

    @Singleton
    @Provides
    fun provideDBDriver(
        @ApplicationContext context: Context
    ): SqlDriver = AndroidSqliteDriver(Database.Schema, context, "NewsApp.db")

    @Singleton
    @Provides
    fun provideDatabase(
        driver: SqlDriver
    ): Database = Database(driver)
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