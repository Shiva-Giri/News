package com.example.news.utils

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun toast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun formatTimeAgo(date1: String): String {  // Note : date1 must be in   "yyyy-MM-dd hh:mm:ss"   format
    var conversionTime =""
    try{
        val format = "yyyy-MM-dd'T'HH:mm:ss'Z'"

        val sdf = SimpleDateFormat(format)

        val datetime= Calendar.getInstance()
        var date2= sdf.format(datetime.time).toString()

        val dateObj1 = sdf.parse(date1)
        val dateObj2 = sdf.parse(date2)
        val diff = dateObj2.time - dateObj1.time

        val diffDays = diff / (24 * 60 * 60 * 1000)
        val diffhours = diff / (60 * 60 * 1000)
        val diffmin = diff / (60 * 1000)
        val diffsec = diff  / 1000
        if(diffDays>1){
            conversionTime+=diffDays.toString()+" days "
        }else if(diffhours>1){
            conversionTime+=(diffhours-diffDays*24).toString()+" hours "
        }else if(diffmin>1){
            conversionTime+=(diffmin-diffhours*60).toString()+" min "
        }else if(diffsec>1){
            conversionTime+=(diffsec-diffmin*60).toString()+" sec "
        }
    }catch (ex:java.lang.Exception){
        Log.e("formatTimeAgo",ex.toString())
    }
    if(conversionTime!=""){
        conversionTime+="ago"
    }
    return conversionTime
}
//For going to the news source page
//val builder = CustomTabsIntent.Builder()
//val customTabsIntent = builder.build()
//customTabsIntent.launchUrl(context, Uri.parse(articleResponse.url))


