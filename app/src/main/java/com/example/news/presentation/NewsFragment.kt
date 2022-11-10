package com.example.news.presentation

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.news.BuildConfig
import com.example.news.R
import com.example.news.data.model.Article
import com.example.news.databinding.FragmentNewsBinding
import com.example.news.utils.formatTimeAgo
import com.example.news.utils.toast
import com.google.gson.Gson
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private var article: Article? = null
    lateinit var imageURL: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newsList = arguments?.getString("newsList")
        article = Gson().fromJson(newsList, Article::class.java)

        article?.let {

            binding.tvNewsDescription.setText(it.title)
            binding.authorName.setText(it.author)
            binding.tvNewsContent.setText(it.content)

            val timeAgo = formatTimeAgo(it.publishedAt)
            binding.tvDate.setText(timeAgo)

            Glide.with(requireActivity())
                .load(it.urlToImage)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(binding.ivImage)

            val articleUrl : String = it.url
            binding.continueRead.setOnClickListener{
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(articleUrl))
                requireActivity().startActivity(browserIntent)
            }
            binding.ivShare.setOnClickListener{
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT,articleUrl)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                requireActivity().startActivity(shareIntent)
            }
                imageURL = it.urlToImage
        }

        binding.tvSaveImage.setOnClickListener {
            Glide.with(this)
                .asBitmap()
                .load(imageURL)
                .into(object : SimpleTarget<Bitmap>(1920, 1080) {

                    override fun onResourceReady(bitmap: Bitmap, transition: Transition<in Bitmap>?) {
                        saveImage(bitmap)
                    }
                })
         }
    }

    internal fun saveImage(image: Bitmap) {
        val savedImagePath: String

        val imageFileName = System.currentTimeMillis().toString() + ".jpg"
        val storageDir = File(
            Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES).toString() + "/Folder Name")
        var success = true
        if (!storageDir.exists()) {
            success = storageDir.mkdirs()
        }
        if (success) {
            val imageFile = File(storageDir, imageFileName)
            savedImagePath = imageFile.absolutePath
            try {
                val fOut = FileOutputStream(imageFile)
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
                fOut.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

           galleryAddPic(savedImagePath)

        }
    }

    private fun galleryAddPic(imagePath: String) {
        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val f = File(imagePath)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val contentUri = FileProvider.getUriForFile(requireContext(), BuildConfig.APPLICATION_ID + ".provider", f)
            mediaScanIntent.data = contentUri
            requireContext().sendBroadcast(mediaScanIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION))
            toast(requireActivity(),"Image Saved to gallery >N")
        }else {
            val contentUri: Uri = Uri.fromFile(f)
            mediaScanIntent.data = contentUri
            requireContext().sendBroadcast(mediaScanIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION))
            toast(requireActivity(),"Image Saved to gallery!")
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}