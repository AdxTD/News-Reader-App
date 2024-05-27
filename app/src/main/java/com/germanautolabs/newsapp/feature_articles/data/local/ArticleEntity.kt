package com.germanautolabs.newsapp.feature_articles.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ArticleEntity (
    val source: String?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val content: String?,
    @ColumnInfo(name = "url_image") val urlToImage: String?,
    @ColumnInfo(name = "published_at") val publishedAt: String,
    @PrimaryKey val id: Int? = null,
)