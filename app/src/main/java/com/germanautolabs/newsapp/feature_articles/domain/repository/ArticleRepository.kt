package com.germanautolabs.newsapp.feature_articles.domain.repository


import com.germanautolabs.newsapp.core.common.Resource
import com.germanautolabs.newsapp.feature_articles.domain.model.ArticleDetails
import com.germanautolabs.newsapp.feature_articles.domain.model.ArticleListing
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    suspend fun getArticleListings(
        fetchFromRemote: Boolean,
        searchQuery: String
    ): Flow<Resource<List<ArticleListing>>>

    suspend fun getArticleDetailsById(
        id: Int
    ): Resource<ArticleDetails>
}