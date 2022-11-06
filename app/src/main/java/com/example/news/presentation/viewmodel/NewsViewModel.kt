package com.example.news.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.data.model.News
import com.example.news.data.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NewsViewModel(private val repository: NewsRepository) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO){
            repository.getPosts()
        }
    }

    val viewModelNews : LiveData<News>
        get() = repository.posts
}