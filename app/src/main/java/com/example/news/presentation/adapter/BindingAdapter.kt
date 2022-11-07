package com.example.news.presentation.adapter

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.news.R
import com.example.news.utils.formatTimeAgo
import javax.inject.Inject

class BindingAdapter @Inject constructor() {
    companion object {
        @BindingAdapter("urlToImage")
        @JvmStatic
        fun loadImage(view: ImageView, urlToImage: String?) {
            try {
                Glide.with(view.context).setDefaultRequestOptions(RequestOptions())
                    .load(urlToImage).placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(view)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }




    }
}