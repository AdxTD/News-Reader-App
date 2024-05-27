package com.germanautolabs.newsapp.feature_articles.data.local

class NewsDaoFake: NewsDao {

    private var articles = emptyList<ArticleEntity>()
    override suspend fun insertArticles(articleEntities: List<ArticleEntity>) {
        articles += articleEntities
    }

    override suspend fun clearArticles() {
        articles = emptyList()
    }

    override suspend fun getArticles(searchQuery: String): List<ArticleEntity>
        = articles.filter {
            it.title!!.lowercase().contains(searchQuery.lowercase())
        }


    override suspend fun getArticleById(id: Int): ArticleEntity? =
        articles.find {
            it.id == id
        }

}