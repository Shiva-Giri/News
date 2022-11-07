package com.example.news.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.news.R
import com.example.news.databinding.FragmentHomeBinding
import com.example.news.presentation.adapter.NewsAdapter
import com.example.news.presentation.viewmodel.NewsViewModel
import com.example.news.utils.Resource
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, NewsViewModel>() {

    override val viewModel: NewsViewModel by viewModels()
   // private val viewModel by activityViewModels<NewsViewModel>()

    lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

         newsAdapter.onItemClick = {
                 val bundle = Bundle()
                    bundle.putString("newsList",Gson().toJson(it))
                    findNavController().navigate(R.id.action_homeFragment_to_newsFragment,bundle)
             }
    }

    private fun initViews() = with(binding) {
        newsAdapter = NewsAdapter(requireContext())
        rvHome.apply {
            setHasFixedSize(true)
            adapter = newsAdapter
            setupobserver()
        }
    }

    private fun setupobserver() = with(binding) {
        viewModel.newsData.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    progressBarStatus(false)
                    tryAgainStatus(false)
                    newsAdapter.differ.submitList(response.data!!.articles)
                    rvHome.adapter = newsAdapter
                }
                is Resource.Error -> {
                    tryAgainStatus(true, response.message!!)
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

    private fun swiperefershnews() {
        swipereferesh_news.setOnRefreshListener {
            viewModel.getNews()
            swipereferesh_news.isRefreshing = false
        }
    }
    private fun tryAgainStatus(status: Boolean, message: String = "message") {
        if (status) {
            tryAgainMessage.text = message
            tryAgainLayout.visibility = View.VISIBLE

        } else {
            tryAgainLayout.visibility = View.GONE
        }
    }

    private fun progressBarStatus(status: Boolean) {
        progressBar_news.visibility = if (status) View.VISIBLE else View.GONE

    }
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater, container, false)
}