package com.example.news.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.news.BuildConfig
import com.example.news.api.NewsService
import com.example.news.data.model.News
import com.example.news.utils.Resource
import org.json.JSONObject
import javax.inject.Inject


class NewsRepository @Inject constructor(private val newsService: NewsService) {


    private val _newsLiveData = MutableLiveData<Resource<News>>()
    val newsLiveData get() = _newsLiveData


     suspend fun getNews() {

        _newsLiveData.postValue(Resource.Loading())
        val response = newsService.getNews("us",BuildConfig.API_KEY)
        if (response.isSuccessful && response.body() != null) {
            _newsLiveData.postValue(Resource.Success(response.body()!!))
        }
        else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _newsLiveData.postValue(Resource.Error(errorObj.getString("message")))
        }
        else {
            _newsLiveData.postValue(Resource.Error("Something Went Wrong"))
        }
    }

}