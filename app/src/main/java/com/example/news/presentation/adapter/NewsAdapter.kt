package com.example.news.presentation.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news.R
import com.example.news.data.model.Article
import com.example.news.databinding.NewsItemBinding

class NewsAdapter(val context: Context, val newsList: List<Article>) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    var onItemClick : ((Article) ->Unit)? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            var itemView = LayoutInflater.from(context).inflate(R.layout.news_item,parent,false)
            return ViewHolder(itemView)

        }
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val news = newsList[position]

            with(holder) {
                Glide.with(context)
                    .load(news.urlToImage)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(iv_image)

                tv_newsTitle.text = news.title

                cv_image.setOnClickListener {
                    onItemClick?.invoke(news)
                }
            }
        }
        override fun getItemCount(): Int {
            return newsList.size
        }

        class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

            var iv_image: ImageView
            var tv_newsTitle: TextView
            var cv_image : CardView

            init {
                iv_image = itemView.findViewById(R.id.iv_image)
                tv_newsTitle = itemView.findViewById(R.id.tv_newsTitle)
                cv_image = itemView.findViewById(R.id.cv_image)
            }

        }

}