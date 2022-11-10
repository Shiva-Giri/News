package com.example.news.presentation

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.news.BuildConfig
import com.example.news.R
import com.example.news.data.model.Article
import com.example.news.databinding.FragmentNewsBinding
import com.example.news.utils.formatTimeAgo
import com.example.news.utils.showPermissionRequestDialog
import com.example.news.utils.toast
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private var article: Article? = null
    lateinit var imageURL: String

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

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

            imageURL = it.urlToImage
            setPermissionCallback()

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

        }

        binding.tvSaveImage.setOnClickListener {

            checkPermissionAndDownloadBitmap(imageURL)
         }
    }
    private fun setPermissionCallback() {
        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getBitmapfromUrl(imageURL)
            }
        }
    }
    private fun checkPermissionAndDownloadBitmap(bitmapURL: String) {
        when {
            ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED -> {

                 getBitmapfromUrl(bitmapURL)
              }
            shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE) -> {
                showPermissionRequestDialog(
                    requireActivity(),
                    getString(R.string.permission_title),
                    getString(R.string.write_permission_request)
                ) {
                    requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    }

    private fun getBitmapfromUrl(bitmapURL: String) = lifecycleScope.launch  {
        Glide.with(requireActivity())
            .asBitmap()
            .load(bitmapURL)
            .into(object : CustomTarget<Bitmap>(1920, 1080) {
                override fun onResourceReady(bitmap: Bitmap, transition: Transition<in Bitmap>?) {

                    saveMediaToStorage(bitmap)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    TODO("Not yet implemented")
                }
            })
    }

    private fun saveMediaToStorage(bitmap: Bitmap) {
        val filename = "${System.currentTimeMillis()}.jpg"
        var fos: OutputStream? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
        {
            requireActivity().contentResolver?.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

              val image = File(imageUri?.getPath(), filename)
               val fil = File(image.absolutePath)
              MediaScannerConnection.scanFile(context, arrayOf(fil.toString()), arrayOf(fil.getName()), null)

                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        }
        else {
            val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            val file = File(image.absolutePath)
            MediaScannerConnection.scanFile(context, arrayOf(file.toString()), arrayOf(file.getName()), null)

            fos = FileOutputStream(image)
        }
        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            toast(requireActivity(),"Saved to Photos!")
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}