package com.germanautolabs.newsapp.feature_articles.domain.usecases

import com.germanautolabs.newsapp.feature_articles.data.local.ArticleEntity
import com.germanautolabs.newsapp.feature_articles.data.repository.FakeArticlesRepository
import com.germanautolabs.newsapp.feature_articles.domain.utils.ArticlesOrder
import com.germanautolabs.newsapp.feature_articles.domain.utils.OrderType
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before

import org.junit.Test

class GetArticlesUseCaseTest {

    private lateinit var getArticlesUseCase: GetArticlesUseCase
    private lateinit var fakeArticlesRepository: FakeArticlesRepository

    @Before
    fun setUp() {
        fakeArticlesRepository = FakeArticlesRepository()
        getArticlesUseCase = GetArticlesUseCase(fakeArticlesRepository)

        val articlesToInsert = mutableListOf<ArticleEntity>()
        ('a'..'z').forEachIndexed { index, c ->
            articlesToInsert.add(
                ArticleEntity(
                    title = "$c",
                    author = null,
                    source = null,
                    urlToImage = null,
                    url = null,
                    content = null,
                    description = null,
                    publishedAt = if(index < 10)  "2024-05-26T12:32:0${index}Z"
                    else "2024-05-26T12:32:${index}Z"
                )
            )
        }
        articlesToInsert.shuffle()
        fakeArticlesRepository.insertFakeArticles(articlesToInsert)
    }

    @Test
    fun `Order articles by title ascending, correct order`() =
        runBlocking {
            val articles = getArticlesUseCase(ArticlesOrder.Title(OrderType.Ascending)).first().data

            for(i in 0..articles!!.size-2){
                assertThat(articles[i].title).isLessThan(articles[i+1].title)
            }
        }

    @Test
    fun `Order articles by title descending, correct order`() =
        runBlocking {
            val articles = getArticlesUseCase(ArticlesOrder.Title(OrderType.Descending)).first().data

            for(i in 0..articles!!.size-2){
                assertThat(articles[i+1].title).isLessThan(articles[i].title)
            }
        }

    @Test
    fun `Order articles by date ascending, correct order`() =
        runBlocking {
            val articles = getArticlesUseCase(ArticlesOrder.Date(OrderType.Ascending)).first().data

            for(i in 0..articles!!.size-2){
                assertThat(articles[i].publishedAt).isLessThan(articles[i+1].publishedAt)
            }
        }


    @Test
    fun `Order articles by date descending, correct order`() =
        runBlocking {
            val articles = getArticlesUseCase(ArticlesOrder.Date(OrderType.Descending)).first().data

            for(i in 0..articles!!.size-2){
                assertThat(articles[i+1].publishedAt).isLessThan(articles[i].publishedAt)
            }
        }

}

