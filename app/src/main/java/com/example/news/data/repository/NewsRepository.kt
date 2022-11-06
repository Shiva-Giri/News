package com.example.news.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.news.api.NewsService
import com.example.news.data.model.News
import com.example.news.utils.NetworkUtils


class NewsRepository(

    private val newsService: NewsService,
    private val applicationContext: Context
) {
    private val postsLiveData = MutableLiveData<News>()

    val posts: LiveData<News>
        get() = postsLiveData

    suspend fun getPosts(){

        if(NetworkUtils.isInternetAvailable(applicationContext))
        {
            val result = newsService.getNews("us")
            if(result.body() != null) {

//                val gson = Gson()
//                Log.d("res==", "getPosts: "+ gson.toJson(result.body()!!))
                postsLiveData.postValue(result.body())
            }
        }
        else{
                    //Network Check
        }

    }
}