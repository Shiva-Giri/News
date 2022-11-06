package com.example.news.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.news.R
import com.example.news.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  setContentView(R.layout.activity_main)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


         setSupportActionBar(binding.toolbar)

            val navController = findNavController(R.id.nav_host_fragment)
            val config = AppBarConfiguration(navController.graph)
            binding.toolbar.setupWithNavController(navController,config)
    }
}