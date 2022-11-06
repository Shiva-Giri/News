package com.example.news.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news.R
import com.example.news.data.model.Article
import com.example.news.databinding.NewsItemBinding

class NewsAdapter(val context: Context, val newsList: List<Article>) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    var onItemClick : ((Article) ->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
         val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
         return ViewHolder(binding)

    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val news = newsList[position]

        with(holder) {

            Glide.with(context)
                .load(news.urlToImage)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(binding.ivImage)

            binding.tvNewsTitle.text = news.title

            binding.cvImage.setOnClickListener {
                onItemClick?.invoke(news)
            }
        }
    }
    override fun getItemCount(): Int {
        return newsList.size
    }

    inner class ViewHolder(val binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root)
  //  class ViewHolder(binding: NewsItemBinding): RecyclerView.ViewHolder(binding.root)
}