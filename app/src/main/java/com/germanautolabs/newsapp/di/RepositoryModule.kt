package com.germanautolabs.newsapp.di

import com.germanautolabs.newsapp.feature_articles.data.repository.ArticleRepositoryImpl
import com.germanautolabs.newsapp.feature_articles.domain.repository.ArticleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindNewsRepository(
        newsRepositoryImpl: ArticleRepositoryImpl
    ): ArticleRepository
}