package com.germanautolabs.newsapp.feature_articles.data.remote.dto

data class NewsResponse (
    val status: String,
    val message: String?,
    val totalResults: Int,
    val articles: List<ArticleDto>
)