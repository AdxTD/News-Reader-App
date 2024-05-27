package com.germanautolabs.newsapp.feature_articles.presentation.article_listing

import com.germanautolabs.newsapp.feature_articles.domain.model.ArticleListing
import com.germanautolabs.newsapp.feature_articles.domain.utils.ArticlesOrder
import com.germanautolabs.newsapp.feature_articles.domain.utils.OrderType


data class ArticleListingsState(
    val articles: List<ArticleListing> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = "",
    val error: String = "",
    val articlesOrder: ArticlesOrder = ArticlesOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)
