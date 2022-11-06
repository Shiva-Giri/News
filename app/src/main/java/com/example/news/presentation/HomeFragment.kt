package com.example.news.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news.NewsApplication
import com.example.news.databinding.FragmentHomeBinding
import com.example.news.databinding.FragmentNewsBinding
import com.example.news.presentation.adapter.NewsAdapter
import com.example.news.presentation.viewmodel.NewsViewModel
import com.example.news.presentation.viewmodel.NewsViewModelFactory

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
//              binding.rvPosts.setHasFixedSize(true)
//         linearlayoutManager = LinearLayoutManager(activity)
//         binding.rvPosts.layoutManager = linearlayoutManager
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = (activity?.application as NewsApplication).newsRepository
        newsViewModel = ViewModelProvider(requireActivity(),
                                    NewsViewModelFactory(repository)).get(NewsViewModel::class.java)

        newsViewModel.viewModelNews.observe(requireActivity(), Observer {
//            postAdapter = PostAdapter(requireActivity(),it)
//            postAdapter.notifyDataSetChanged()
//            binding.rvPosts.adapter = postAdapter

        })
        newsAdapter.onItemClick = {

         /*   val bundle = Bundle()

            bundle.putString("userId",it.userId.toString())
            bundle.putString("id",it.id.toString())
            bundle.putString("title",it.title)
            bundle.putString("body",it.body)
            val pdf = PostDetailFragment()

            pdf.arguments = bundle
            //   fragmentManager
            getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment,pdf)
                .addToBackStack(null)
                .commit()*/

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}