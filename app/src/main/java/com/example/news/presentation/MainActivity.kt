package com.example.news.presentation


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.news.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setContentView(R.layout.activity_main)


    //    val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
      //  val navController = navHostFragment.navController

      //  val navController = findNavController(R.id.nav_host_fragment)

    //   val appBarConfiguration = AppBarConfiguration(navController.graph)
   //     findViewById<Toolbar>(R.id.toolbar).setupWithNavController(navController, appBarConfiguration)

    }

}