package com.germanautolabs.newsapp.feature_articles.data.mapper

import com.germanautolabs.newsapp.feature_articles.data.local.ArticleEntity
import com.germanautolabs.newsapp.feature_articles.data.remote.dto.ArticleDto
import com.germanautolabs.newsapp.feature_articles.domain.model.ArticleDetails
import com.germanautolabs.newsapp.feature_articles.domain.model.ArticleListing


fun ArticleDto.toArticleEntity(): ArticleEntity {
    return ArticleEntity(
        source = source.name,
        author = author,
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        content = content,
        publishedAt = publishedAt,
    )
}

fun ArticleEntity.toArticleListing(): ArticleListing {
    return ArticleListing(
        source = source,
        title = title,
        description = description,
        publishedAt = publishedAt,
        id = id,
    )
}

fun ArticleEntity.toArticleDetails(): ArticleDetails {
    return ArticleDetails(
        source = source,
        title = title,
        description = description,
        publishedAt = publishedAt,
        author = author,
        content = content,
        url = url,
        urlToImage = urlToImage,
    )
}