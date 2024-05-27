package com.germanautolabs.newsapp.feature_articles.presentation.article_details

import com.germanautolabs.newsapp.feature_articles.domain.model.ArticleDetails


data class ArticleDetailsState(
    val articleDetails: ArticleDetails? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
