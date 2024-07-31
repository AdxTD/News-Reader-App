package com.germanautolabs.newsapp.feature_articles.presentation.article_listing

import com.germanautolabs.newsapp.feature_articles.domain.utils.ArticlesOrder


sealed class ArticleListingsEvent {
    data object Refresh: ArticleListingsEvent()
    data class OnSearchQueryChange(val query: String): ArticleListingsEvent()
    data class Order(val articlesOrder: ArticlesOrder): ArticleListingsEvent()
    data object ToggleOrderSection: ArticleListingsEvent()
}
