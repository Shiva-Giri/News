package com.example.news.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.news.NewsApplication
import com.example.news.data.model.News
import com.example.news.data.repository.NewsRepository
import com.example.news.utils.Resource
import com.example.news.utils.hasInternetConnection
import com.example.news.utils.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject


class NewsViewModel @Inject constructor(
    application: Application,
    private val repository: NewsRepository
    ): AndroidViewModel(application) {

    val newsData: MutableLiveData<Resource<News>> = MutableLiveData()

    private val newsDataTemp = MutableLiveData<Resource<News>>()

    init {
        getNews()
    }

    fun getNews() = viewModelScope.launch {
        fetchNews()
    }

    private suspend fun fetchNews() {
        newsData.postValue(Resource.Loading())
        try {
            if (hasInternetConnection<NewsApplication>()) {
                val response = repository.getNews()
                newsDataTemp.postValue(Resource.Success(response.body()!!))
                newsData.postValue(handleNewsResponse(response))
            } else {
                newsData.postValue(Resource.Error("No Internet Connection"))
                toast(getApplication(), "No Internet Connection.!")
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> newsData.postValue(
                    Resource.Error(
                        t.message!!
                    )
                )
                else -> newsData.postValue(
                    Resource.Error(
                        t.message!!
                    )
                )
            }
        }
    }
    private fun handleNewsResponse(response: Response<News>): Resource<News>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())

    }
}