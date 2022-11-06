package com.example.news

import android.app.Application
import com.example.news.api.NewsService
import com.example.news.api.RetrofitHelper
import com.example.news.data.repository.NewsRepository


class NewsApplication : Application() {

    lateinit var newsRepository: NewsRepository

    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        val newsService = RetrofitHelper.getInstance().create(NewsService::class.java)
        newsRepository = NewsRepository(newsService, applicationContext)
    }
}