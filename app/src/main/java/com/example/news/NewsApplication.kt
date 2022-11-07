package com.example.news

import android.app.Application
import com.example.news.api.NewsService
import com.example.news.api.RetrofitHelper
import com.example.news.data.repository.NewsRepository
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NewsApplication : Application() {

}