package com.example.news.data.repository

import com.example.news.api.NewsService
import javax.inject.Inject


class NewsRepository @Inject constructor(private val newsService: NewsService) {

    suspend fun getNews() = newsService.getNews()

}