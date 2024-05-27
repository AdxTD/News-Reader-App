package com.germanautolabs.newsapp.feature_articles.domain.usecases

import com.germanautolabs.newsapp.core.common.Resource
import com.germanautolabs.newsapp.feature_articles.domain.model.ArticleDetails
import com.germanautolabs.newsapp.feature_articles.domain.repository.ArticleRepository


class GetArticleDetailsUseCase(
    private val articleRepository: ArticleRepository
) {
    suspend operator fun invoke(articleId: Int): Resource<ArticleDetails> =
        articleRepository.getArticleDetailsById(articleId)
}