package com.example.news.presentation.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news.R
import com.example.news.data.model.Article
import com.example.news.databinding.NewsItemBinding

class NewsAdapter(val context: Context) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

     var onItemClick : ((Article) ->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.NewsViewHolder {
        return NewsViewHolder(
            NewsItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
    override fun onBindViewHolder(holder: NewsAdapter.NewsViewHolder, position: Int) {
        val news = differ.currentList[position]
        holder.bind(news)

    }
        /*override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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
        }*/

    private val differCallback =
        object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(
                oldItem: Article,
                newItem: Article
            ): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(
                oldItem: Article,
                newItem: Article
            ): Boolean {
                return oldItem == newItem
            }

        }

    val differ = AsyncListDiffer(this, differCallback)

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    inner class NewsViewHolder(private val itemsNewsBinding: NewsItemBinding) :
        RecyclerView.ViewHolder(itemsNewsBinding.root) {
        fun bind(articleResponse: Article) {
            itemsNewsBinding.apply {
                itemsNewsBinding.news = articleResponse
                itemsNewsBinding.executePendingBindings()



                itemsNewsBinding.cvImage.setOnClickListener {
                    onItemClick?.invoke(articleResponse)

                }


            }

        }
    }

}