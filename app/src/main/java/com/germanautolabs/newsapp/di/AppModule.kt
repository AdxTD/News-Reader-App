package com.germanautolabs.newsapp.di

import android.app.Application
import androidx.room.Room
import com.germanautolabs.newsapp.feature_articles.data.local.NewsDatabase
import com.germanautolabs.newsapp.feature_articles.data.remote.NewsApi
import com.germanautolabs.newsapp.feature_articles.domain.repository.ArticleRepository
import com.germanautolabs.newsapp.feature_articles.domain.usecases.GetArticlesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi {
        return Retrofit.Builder()
            .baseUrl(NewsApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideNewsDatabase(app: Application) : NewsDatabase {
        return Room.databaseBuilder(
            app,
            NewsDatabase::class.java,
            "newsdb.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideGetArticlesUseCase(
        articlesRepository: ArticleRepository
    ): GetArticlesUseCase {
        return GetArticlesUseCase(articlesRepository)
    }
}