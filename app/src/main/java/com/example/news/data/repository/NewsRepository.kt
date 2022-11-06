package com.example.news.data.repository

import android.content.Context
import android.util.Log.d
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.news.api.NewsService
import com.example.news.data.model.News
import com.example.news.utils.NetworkUtils
import com.example.news.utils.Resource
import com.example.news.utils.toast
import com.google.gson.Gson
import java.io.IOException


class NewsRepository(

    private val newsService: NewsService,
    private val applicationContext: Context
) {
    private val newsLiveData = MutableLiveData<News>()

    val news: LiveData<News>
        get() = newsLiveData

    suspend fun getNews(){
        try {
            if(NetworkUtils.isInternetAvailable(applicationContext))
            {
                val result = newsService.getNews("d29d58aab88d4ea0b04ddb245a230068","us")
                if(result.body()!= null) {

                    val gson = Gson()
                    d("res==", "getNews: "+ gson.toJson(result))
                    newsLiveData.postValue(result.body())
                }
            }
            else{
                toast(applicationContext, "No Internet Connection.!")
            }
        } catch (t: Throwable) {
            toast(applicationContext, "Exception:"+t.message!!)
        }


    }
}