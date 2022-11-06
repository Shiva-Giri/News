package com.example.news

import android.app.Application
import com.example.news.api.NewsService
import com.example.news.data.repository.NewsRepository

import com.example.testposts.api.RetrofitHelper


class NewsApplication : Application() {

    lateinit var newsRepository: NewsRepository

    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        val postService = RetrofitHelper.getInstance().create(NewsService::class.java)

        newsRepository = NewsRepository(postService, applicationContext)
    }
}