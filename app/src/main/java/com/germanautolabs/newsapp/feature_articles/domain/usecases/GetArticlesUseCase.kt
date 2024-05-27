package com.germanautolabs.newsapp.feature_articles.domain.usecases

import com.germanautolabs.newsapp.core.common.Resource
import com.germanautolabs.newsapp.feature_articles.domain.model.ArticleListing
import com.germanautolabs.newsapp.feature_articles.domain.repository.ArticleRepository
import com.germanautolabs.newsapp.feature_articles.domain.utils.ArticlesOrder
import com.germanautolabs.newsapp.feature_articles.domain.utils.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetArticlesUseCase(
    private val articleRepository: ArticleRepository
) {

    suspend operator fun invoke(
        articlesOrder: ArticlesOrder = ArticlesOrder.Date(orderType = OrderType.Descending),
        fetchFromRemote: Boolean = false,
        searchQuery: String = "",
    ): Flow<Resource<List<ArticleListing>>> =

        articleRepository.getArticleListings(fetchFromRemote, searchQuery).map { resource ->
            when (articlesOrder.orderType) {
                is OrderType.Ascending -> when (articlesOrder) {
                    is ArticlesOrder.Title -> resource.data =
                        resource.data?.sortedBy { it.title?.lowercase() }

                    is ArticlesOrder.Date -> resource.data =
                        resource.data?.sortedBy { it.publishedAt }
                }

                is OrderType.Descending -> when (articlesOrder) {
                    is ArticlesOrder.Title -> resource.data =
                        resource.data?.sortedByDescending { it.title?.lowercase() }

                    is ArticlesOrder.Date -> resource.data =
                        resource.data?.sortedByDescending { it.publishedAt }
                }
            }
            resource
        }

}