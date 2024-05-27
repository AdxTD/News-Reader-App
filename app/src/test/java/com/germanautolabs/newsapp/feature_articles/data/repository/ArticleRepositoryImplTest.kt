package com.germanautolabs.newsapp.feature_articles.data.repository

import app.cash.turbine.test
import com.germanautolabs.newsapp.core.common.Resource
import com.google.common.truth.Truth.assertThat
import com.germanautolabs.newsapp.feature_articles.data.local.ArticleEntity
import com.germanautolabs.newsapp.feature_articles.data.local.NewsDao
import com.germanautolabs.newsapp.feature_articles.data.local.NewsDaoFake
import com.germanautolabs.newsapp.feature_articles.data.local.NewsDatabase
import com.germanautolabs.newsapp.feature_articles.data.mapper.toArticleListing
import com.germanautolabs.newsapp.feature_articles.data.remote.NewsApi
import com.germanautolabs.newsapp.feature_articles.data.remote.dto.ArticleDto
import com.germanautolabs.newsapp.feature_articles.data.remote.dto.NewsResponse
import com.germanautolabs.newsapp.feature_articles.data.remote.dto.SourceDto
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ArticleRepositoryImplTest {

    private val articleEntities = (1..100).map {
        ArticleDto(
            title = "title$it",
            author = "author$it",
            description = "description$it",
            content = "content$it",
            source = SourceDto("id$it","name$it"),
            url = "url$it",
            urlToImage = "urlToImage$it",
            publishedAt = "2024-05-26T12:32:0${it%10}Z"
        )
    }

    private val newsResponse =  NewsResponse(
        totalResults = 100,
        status = "ok",
        articles = articleEntities,
        message = ""
    )

    private lateinit var repository: ArticleRepositoryImpl
    private lateinit var api: NewsApi
    private lateinit var db: NewsDatabase
    private lateinit var newsDao: NewsDao

    @Before
    fun setUp() {
        api = mockk(relaxed = true) {
            coEvery { getNews() } returns newsResponse
        }
        newsDao = NewsDaoFake()
        db = mockk(relaxed = true) {
            every { dao } returns newsDao
        }
        repository = ArticleRepositoryImpl(
            api = api,
            db = db,
        )
    }

    @Test
    fun `Test local database cache with fetch from remote set to true`() = runTest {
        val localEntities = listOf(
            ArticleEntity(
                id = 0,
                title = "test-title",
                author = "test-author",
                description = "test-description",
                content = "test-content",
                source = "test-source",
                url = "test-url",
                urlToImage = "test-urlToImage",
                publishedAt = "2024-05-26T12:32:00Z"
            )
        )
        newsDao.insertArticles(localEntities)
        repository.getArticleListings(
            fetchFromRemote = true,
            searchQuery = ""
        ).test{
            val startLoading = awaitItem()
            assertThat((startLoading as Resource.Loading).isLoading).isTrue()

            val listingsFromDb = awaitItem()
            assertThat(listingsFromDb is Resource.Success).isTrue()
            assertThat(listingsFromDb.data).isEqualTo(localEntities.map { it.toArticleListing() })

            val remoteListingsFromDb = awaitItem()
            assertThat(remoteListingsFromDb is Resource.Success).isTrue()
            assertThat(remoteListingsFromDb.data).isEqualTo(
                newsDao.getArticles("").map { it.toArticleListing() }
            )

            val stopLoading = awaitItem()
            assertThat((stopLoading as Resource.Loading).isLoading).isFalse()

            awaitComplete()
        }
    }
}