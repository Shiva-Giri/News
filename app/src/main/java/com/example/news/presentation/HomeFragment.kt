package com.example.news.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news.R
import com.example.news.data.model.Article
import com.example.news.databinding.FragmentHomeBinding
import com.example.news.presentation.adapter.NewsAdapter
import com.example.news.presentation.viewmodel.NewsViewModel
import com.example.news.utils.Resource
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val newsViewModel by viewModels<NewsViewModel>()
    private lateinit var newsAdapter: NewsAdapter
    lateinit var linearlayoutManager : LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        newsAdapter = NewsAdapter(requireActivity(),::onItemClick)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsViewModel.fetchNews()

        binding.rvHome.setHasFixedSize(true)
        linearlayoutManager = LinearLayoutManager(activity)
        binding.rvHome.layoutManager = linearlayoutManager
        binding.rvHome.adapter = newsAdapter

        bindObservers()
    }

    private fun bindObservers()
    {
      newsViewModel.newsLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                 progressBarStatus(false)
                  tryAgainStatus(false)
                    newsAdapter.submitList(it.data!!.articles)
                 //   binding.rvHome.adapter = newsAdapter
                }
                is Resource.Error -> {
                tryAgainStatus(true, it.message!!)
                progressBarStatus(false)
                }
                is Resource.Loading -> {
                   tryAgainStatus(false)
                   progressBarStatus(true)
                }
            }
        })
       swiperefershnews()
    }

    private fun onItemClick(article: Article) {
        val bundle = Bundle()
        bundle.putString("newsList",Gson().toJson(article))
        findNavController().navigate(R.id.action_homeFragment_to_newsFragment,bundle)
    }

    private fun swiperefershnews() {
        binding.swiperefereshNews.setOnRefreshListener {
            newsViewModel.fetchNews()
            binding.swiperefereshNews.isRefreshing = false
        }
    }
    private fun progressBarStatus(status: Boolean) {
        binding.progressBar.visibility = if (status) View.VISIBLE else View.GONE

    }
    private fun tryAgainStatus(status: Boolean, message: String = "message") {
        if (status) {
            binding.tryAgainMessage.text = message
            binding.tryAgainLayout.visibility = View.VISIBLE

        } else {
            binding.tryAgainLayout.visibility = View.INVISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}