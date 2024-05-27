package com.germanautolabs.newsapp.feature_articles.data.remote

import com.germanautolabs.newsapp.feature_articles.data.remote.dto.NewsResponse
import retrofit2.http.GET

interface NewsApi {

    companion object {
        const val API_KEY = "810cb727950544ec8300dc01448c4988"
        const val BASE_URL = "https://newsapi.org/v2/"
    }

    @GET("top-headlines?country=us&apiKey=${API_KEY}")
    suspend fun getNews(): NewsResponse
}