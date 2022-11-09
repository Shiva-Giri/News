package com.example.news.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.news.NewsApplication
import com.example.news.data.model.News
import com.example.news.data.repository.NewsRepository
import com.example.news.utils.Resource
import com.example.news.utils.hasInternetConnection
import com.example.news.utils.toast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel()
    {
        val newsLiveData get() = newsRepository.newsLiveData

        fun fetchNews() {
            viewModelScope.launch {
                newsRepository.getNews()
            }

        }
    }