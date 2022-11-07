package com.example.news.data.model

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import java.io.Serializable


data class News(
    val status: String,
    val totalResult: Int,
    val articles: MutableList<Article>,
)

data class Article(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
): Serializable


data class Source(
    val id: String,
    val name: String,
):Serializable

  /*  @BindingAdapter("ArticleImage")
    fun loadImage(view: ImageView, urlToImage: String?) {
        Glide.with(view.getContext())
            .load(urlToImage).apply(RequestOptions().circleCrop())
            .into(view)
    }*/