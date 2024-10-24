package com.example.assignment.presentation

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.assignment.data.VedioRepoImp
import com.example.assignment.domain.usecase.GetAllVidiosFromLocalCase
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment.databinding.ActivityMainBinding
import com.example.assignment.presentation.adapter.VedioAdapter
import com.example.assignment.presentation.viewModel.VedioViewModel

/**
 * Created by Rishi Porwal
 */
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private val vedioViewMode: VedioViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val videoRepository = VedioRepoImp(contentResolver)
                val getAllVideosUseCase = GetAllVidiosFromLocalCase(videoRepository)
                return VedioViewModel(getAllVideosUseCase) as T
            }
        }
    }

   // This Method use for register request for video read permission
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            vedioViewMode.fetchAllVedios()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inProgessLoader()
        setUpView()
        checkPermissionsAndLoadVideos()
    }


    private fun setUpView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            vedioViewMode.videoListLiveData.observe(this@MainActivity) {
                adapter = VedioAdapter(it, { openVedio(it.uri) })
            }
        }

    }

    // hide and show progress bar
    private fun inProgessLoader() {
        vedioViewMode.isLoadingLiveData.observe(this@MainActivity) { isLoading ->
            binding.apply {
                progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
                recyclerView.visibility = if (isLoading) View.GONE else View.VISIBLE
            }
        }
    }

    //when click on vedio in list then Open external video player (here i used mx player for external player)
    fun openVedio(uri: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(Uri.parse(uri), "video/*")
            setPackage("com.mxtech.videoplayer.ad")
        }
        startActivity(intent)
    }

    private fun checkPermissionsAndLoadVideos() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_VIDEO)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        } else {
            vedioViewMode.fetchAllVedios()
        }
    }

}