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
import dagger.assisted.Assisted
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import javax.inject.Singleton

val NewsModule = module {
    single<NewsRemoteDataSource> { NewsRemoteDataSourceImpl() }
    single<NewsRepository> { NewsRepositoryImpl(get()) }

    viewModel { CategoryListViewModelImpl(get()) }
    viewModel { params ->
        NewsListViewModelImpl(get(), params.get()) }
    viewModel { NewsViewModelImpl(get(), get()) }
}


//@Module
//@InstallIn(SingletonComponent::class)
//abstract class AppBindModule {
//    @Singleton
//    @Binds
//    abstract fun bindNewsRemoteDataSource(
//        impl: NewsRemoteDataSourceImpl
//    ): NewsRemoteDataSource
//
//}
//
//@Module
//@InstallIn(SingletonComponent::class)
//class AppProvideModule {
//    @Singleton
//    @Provides
//    fun provideNewsRepositoryImpl(
//        remoteDS: NewsRemoteDataSource
//    ): NewsRepository = NewsRepositoryImpl(remoteDS)
//}
//
//@Module
//@InstallIn(ViewModelComponent::class)
//abstract class ViewModelProvideModule {
//    @Binds
//    abstract fun bindCategoryListViewModelImpl(
//        impl: CategoryListViewModelImpl
//    ): CategoryListViewModel
//
//    @Binds
//    abstract fun bindNewsListViewModelImpl(
//        impl: NewsListViewModelImpl
//    ): NewsListViewModel
//
//    @Binds
//    abstract fun bindNewsViewModelImpl(
//        impl: NewsViewModelImpl
//    ): NewsViewModel
//
//}