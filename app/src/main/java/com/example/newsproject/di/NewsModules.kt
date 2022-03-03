package com.example.newsproject.di

import com.example.newsproject.data.*
import com.example.newsproject.ui.categoryList.CategoryListViewModelImpl
import com.example.newsproject.ui.news.NewsViewModelImpl
import com.example.newsproject.ui.newsList.NewsListViewModelImpl
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val NewsModule = module {
    single<RetrofitService> {
        val BASE_URL = "http://testtask.sebbia.com/v1/news/"
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitService::class.java)
    }

    single<NewsRemoteDataSource> { NewsRemoteDataSourceImpl(get(), IO) }
    single<NewsRepository> { NewsRepositoryImpl(get(), Default) }

    viewModel { CategoryListViewModelImpl(get()) }
    viewModel { params ->
        NewsListViewModelImpl(get(), params.get())
    }
    viewModel { params ->
        NewsViewModelImpl(get(), params.get())
    }
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