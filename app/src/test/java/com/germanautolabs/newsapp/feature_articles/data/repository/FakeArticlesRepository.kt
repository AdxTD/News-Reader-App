package com.germanautolabs.newsapp.feature_articles.data.repository


import com.germanautolabs.newsapp.core.common.Resource
import com.germanautolabs.newsapp.feature_articles.data.local.ArticleEntity
import com.germanautolabs.newsapp.feature_articles.data.mapper.toArticleDetails
import com.germanautolabs.newsapp.feature_articles.data.mapper.toArticleListing
import com.germanautolabs.newsapp.feature_articles.domain.model.ArticleDetails
import com.germanautolabs.newsapp.feature_articles.domain.model.ArticleListing
import com.germanautolabs.newsapp.feature_articles.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeArticlesRepository : ArticleRepository {

    private val articles = mutableListOf<ArticleEntity>()

    override suspend fun getArticleListings(
        fetchFromRemote: Boolean,
        searchQuery: String
    ): Flow<Resource<List<ArticleListing>>> =
        flow { emit(Resource.Success(
            data = articles.map { it.toArticleListing() }
        ))}

    override suspend fun getArticleDetailsById(id: Int): Resource<ArticleDetails> =
        Resource.Success(data = articles.find {
            it.id == id
        }?.toArticleDetails())

    fun insertFakeArticles(articlesToInsert: List<ArticleEntity>) =
        articles.addAll(articlesToInsert)

}