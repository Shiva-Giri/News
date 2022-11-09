package com.example.news.data.model

import java.io.Serializable


data class News(
    val status: String,
    val totalResult: Int,
    val articles: List<Article>,
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

