package com.example.news.api

import com.example.news.data.model.News
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsService {

    // https://newsapi.org/v2/top-headlines?country=us&apiKey=randomNumbera837489jsjlk4u98


    @GET("v2/top-headlines")
    suspend fun getNews(
        @Query("country")
        countryCode: String?,
        @Query("apiKey")
        apiKey: String?
    ): Response<News>
}