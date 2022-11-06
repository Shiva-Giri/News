package com.example.news.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.news.R
import com.example.news.data.model.Article
import com.example.news.databinding.FragmentNewsBinding
import com.google.gson.Gson

class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private var article: Article? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewsBinding.inflate(inflater, container, false)


       /* val args = this.arguments
        val userId = args?.get("userId")
        val Id = args?.get("id")
        val title = args?.get("title")
        val body = args?.get("body")

        binding.tvUserId.text = "UserId: "+userId.toString()
        binding.tvId.text = "Id: "+Id.toString()
        binding.tvTitle.text = "Title: "+title.toString()
        binding.tvBody.text = body.toString()
*/


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newsList = arguments?.getString("newsList")
        article = Gson().fromJson(newsList, Article::class.java)

        article?.let {

            binding.tvNewsDescription.setText(it.description)
            binding.authorName.setText(it.author)
            binding.tvDate.setText(it.publishedAt)
            binding.tvNewsContent.setText(it.content)

            Glide.with(requireActivity())
                .load(it.urlToImage)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(binding.ivImage)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}