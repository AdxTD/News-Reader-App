package com.germanautolabs.newsapp.feature_articles.domain.model

data class ArticleListing(
    val source: String?,
    val title: String?,
    val description: String?,
    val publishedAt: String,
    val id: Int?
)
