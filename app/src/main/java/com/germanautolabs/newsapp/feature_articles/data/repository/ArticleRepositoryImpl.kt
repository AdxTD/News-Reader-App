package com.germanautolabs.newsapp.feature_articles.data.repository

import com.germanautolabs.newsapp.core.common.Resource
import com.germanautolabs.newsapp.feature_articles.data.local.NewsDatabase
import com.germanautolabs.newsapp.feature_articles.data.mapper.*
import com.germanautolabs.newsapp.feature_articles.data.remote.NewsApi
import com.germanautolabs.newsapp.feature_articles.domain.model.ArticleDetails
import com.germanautolabs.newsapp.feature_articles.domain.model.ArticleListing
import com.germanautolabs.newsapp.feature_articles.domain.repository.ArticleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val api: NewsApi,
    private val db: NewsDatabase
) : ArticleRepository {

    private val dao = db.dao

    override suspend fun getArticleListings(
        fetchFromRemote: Boolean,
        searchQuery: String
    ): Flow<Resource<List<ArticleListing>>> =

        withContext(Dispatchers.IO) {
            flow {
                emit(Resource.Loading(true))
                val localListings = dao.getNews(searchQuery)
                emit(Resource.Success(
                    data = localListings.map { it.toArticleListing() }
                ))

                val isDbEmpty = localListings.isEmpty() && searchQuery.isBlank()
                val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
                if (shouldJustLoadFromCache) {
                    emit(Resource.Loading(false))
                    return@flow
                }

                val remoteListingsResponse = try {
                    api.getNews()
                } catch (e: IOException) {
                    e.printStackTrace()
                    emit(Resource.Error("Couldn't load data, msg: ${e.message}"))
                    null
                } catch (e: HttpException) {
                    e.printStackTrace()
                    emit(Resource.Error("Couldn't load data, msg: ${e.message}"))
                    null
                }

                remoteListingsResponse?.let { response ->
                    if (response.status != "ok") {
                        emit(Resource.Error("Couldn't load data, msg: ${response.message}"))
                        return@flow
                    }
                    dao.clearNews()
                    dao.insertNews(
                        response.articles.map { it.toArticleEntity() }
                    )
                    emit(Resource.Success(
                        data = dao
                            .getNews("")
                            .map { it.toArticleListing() }
                    ))
                    emit(Resource.Loading(false))
                }
            }
        }

    override suspend fun getArticleDetailsById(id: Int): Resource<ArticleDetails> =

        withContext(Dispatchers.IO) {
            try {
                Resource.Success(dao.getArticleById(id).toArticleDetails())
            } catch (exc: IOException) {
                Resource.Error(
                    message = "Couldn't load data, msg: ${exc.message}"
                )
            }
        }

}