package com.germanautolabs.newsapp.feature_articles.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(
        articleEntities: List<ArticleEntity>
    )

    @Query("DELETE FROM articleentity")
    suspend fun clearArticles()

    @Query(
        """
            SELECT * 
            FROM articleentity
            WHERE LOWER(title) LIKE '%' || LOWER(:searchQuery) || '%' OR
                LOWER(:searchQuery) == LOWER(source)
        """
    )
    suspend fun getArticles(searchQuery: String): List<ArticleEntity>

    @Query("SELECT * FROM articleentity WHERE id = :id")
    suspend fun getArticleById(id: Int): ArticleEntity?
}