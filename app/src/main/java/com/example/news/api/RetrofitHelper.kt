package com.example.testposts.api

import android.content.Context
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitHelper {

    private const val BASE_URL = "/https://newsapi.org/"

    @Volatile
    lateinit var appContext: Context

    private val API_KEY =  "d29d58aab88d4ea0b04ddb245a230068"
    val cacheSize = (5 * 1024 * 1024).toLong()

    val myCache = Cache(appContext.cacheDir, cacheSize)
    ////////////////////////////////////////////////////
    private val okHttpClient:OkHttpClient = OkHttpClient().newBuilder()

        .addInterceptor(Interceptor { chain ->
            val originalRequest = chain.request()
            val newRequest = originalRequest.newBuilder()
                .header("apiKey", API_KEY)
                .build()
            chain.proceed(newRequest)
        })

        .build()

    fun getInstance() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
}