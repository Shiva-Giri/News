package com.example.news.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news.NewsApplication
import com.example.news.R
import com.example.news.databinding.FragmentHomeBinding
import com.example.news.presentation.adapter.NewsAdapter
import com.example.news.presentation.viewmodel.NewsViewModel
import com.example.news.presentation.viewmodel.NewsViewModelFactory
import com.google.gson.Gson

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    lateinit var newsViewModel: NewsViewModel

     lateinit var newsAdapter: NewsAdapter
    lateinit var linearlayoutManager : LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.rvHome.setHasFixedSize(true)
        linearlayoutManager = LinearLayoutManager(activity)
        binding.rvHome.layoutManager = linearlayoutManager
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = (activity?.application as NewsApplication).newsRepository

        newsViewModel = ViewModelProvider(requireActivity(),
                                    NewsViewModelFactory(repository)).get(NewsViewModel::class.java)

        Log.d("homefrag", "onViewCreated:of HomeFrag")
        newsViewModel.viewModelNews.observe(requireActivity(), Observer {
            val gson = Gson()

            Log.d("homefrag.observe", "HomeFrag"+gson.toJson(it.articles))
           newsAdapter = NewsAdapter(requireActivity(),it.articles)

            newsAdapter.notifyDataSetChanged()

            binding.rvHome.adapter = newsAdapter

        })
     newsAdapter.onItemClick = {

         val bundle = Bundle()
            bundle.putString("newsList",Gson().toJson(it))
            findNavController().navigate(R.id.action_homeFragment_to_newsFragment,bundle)
//            bundle.putString("description",it.description)
//            bundle.putString("url",it.url)
//            bundle.putString("urlToImage",it.urlToImage)
//            bundle.putString("publishedAt",it.publishedAt)
//            bundle.putString("content",it.content)
//            bundle.putString("author",it.author)
//         val pdf = NewsFragment()
//
//            pdf.arguments = bundle
//            //   fragmentManager
//            getParentFragmentManager()
//                .beginTransaction()
//                .replace(R.id.nav_host_fragment,pdf)
//                .addToBackStack(null)
//                .commit()

    }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}