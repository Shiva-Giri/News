package com.example.news.api

import com.example.news.data.model.News
import com.example.news.utils.API_Key
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsService {

    // https://newsapi.org/v2/top-headlines?country=us&apiKey=d29d58aab88d4ea0b04ddb245a230068

    @GET("v2/top-headlines")
    suspend fun getNews(
        @Query("country")
        countryCode: String = "us",
        @Query("apiKey")
        apiKey: String = API_Key
    ): Response<News>
}