package com.example.news.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.news.databinding.FragmentNewsBinding

class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}