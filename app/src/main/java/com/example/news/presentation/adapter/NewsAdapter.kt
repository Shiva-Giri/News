package com.example.news.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.news.R
import com.example.news.data.model.Article
import com.example.news.data.model.News
import com.example.news.databinding.NewsItemBinding

class NewsAdapter(private val context: Context, private val onItemClick: (Article) -> Unit) :
    ListAdapter<Article, NewsAdapter.NewsViewHolder>(ComparatorDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }


    override fun onBindViewHolder(holder: NewsAdapter.NewsViewHolder, position: Int) {

        val articleItem = getItem(position)
        articleItem?.let {
            holder.bind(it)
        }
    }
     /*
    override fun getItemCount(): Int {
        return differ.currentList.size
    }*/

    inner class NewsViewHolder(private val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root)
      {
       fun bind(article: Article) {
                    binding.tvNewsTitle.text = article.title

                    Glide.with(context)
                        .load(article.urlToImage)
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .into(binding.ivImage)

                    binding.cvImage.setOnClickListener {
                        onItemClick.invoke(article)
                    }
                 }
     }

    class ComparatorDiffUtil : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

  }

