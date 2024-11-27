package com.capstone.scancamanalyze.ui.camera

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.capstone.scancamanalyze.ViewModelFactory
import com.capstone.scancamanalyze.databinding.FragmentCameraBinding
import com.capstone.scancamanalyze.di.Injection
import com.capstone.scancamanalyze.ml.ImageClassifierHelper
import com.capstone.scancamanalyze.ui.home.HomeViewModel

import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class CameraFragment : Fragment() {

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!

    private var imageUri: Uri? = null
    private lateinit var imageClassifierHelper: ImageClassifierHelper

    private val viewModel by viewModels<CameraViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val bitmap = result.data?.extras?.get("data") as? Bitmap
                bitmap?.let {
                    binding.imagePreview.setImageBitmap(it)
//                    classifyImageFromBitmap(it)
                }
            }
        }

    // Launcher untuk galeri
    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                imageUri = it.data?.data
                imageUri?.let { uri ->
                    binding.imagePreview.setImageURI(uri)
//                    classifyImageFromUri(uri)
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraBinding.inflate(inflater, container, false)

        // Inisialisasi ImageClassifierHelper
        setupClassifier()

        // Observasi hasil klasifikasi
        setupObservers()

        // Inisialisasi listener untuk tombol
        setupListeners()

        return binding.root
    }

    private fun setupClassifier() {
        imageClassifierHelper = ImageClassifierHelper(
            threshold = 0.5f,
            maxResults = 2,
            context = requireContext(),
            classifierListener = object : ImageClassifierHelper.ClassifierListener {
                override fun onError(error: String) {
                    Log.e("ImageClassifier", error)
                }

                override fun onResults(results: MutableList<ImageClassifierHelper.Classifications>) {
                    displayResults(results)
                }
            }
        )
    }

    private fun setupObservers() {
        viewModel.text.observe(viewLifecycleOwner) {
            binding.resultText.text = it
        }
    }

    private fun setupListeners() {
        binding.buttonCamera.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraLauncher.launch(cameraIntent)
        }

        binding.buttonGallery.setOnClickListener {
            val galleryIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            galleryLauncher.launch(galleryIntent)
        }

        binding.buttonAnalyze.setOnClickListener {
            imageUri?.let { uri ->
                classifyImageFromUri(uri)
            }
        }
    }

    private fun classifyImageFromUri(uri: Uri) {
        imageClassifierHelper.classifyStaticImage(uri)
    }

    private fun classifyImageFromBitmap(bitmap: Bitmap) {
        val uri = bitmapToTempUri(bitmap)
        uri?.let { classifyImageFromUri(it) }
    }

    private fun displayResults(results: MutableList<ImageClassifierHelper.Classifications>) {
        val resultText = results.joinToString(separator = "\n") { "${it.label}: ${it.score * 100}%" }
        binding.resultText.text = resultText

        // Simpan hasil klasifikasi ke database menggunakan ViewModel
        viewModel.saveAnalyzeData(
            imageName = "Captured Image",
            level = results.maxOf { it.score.times(100).toInt() }, // Contoh level dari skor tertinggi
            predictionResult = results.firstOrNull()?.label ?: "Unknown"
        )
    }

    private fun bitmapToTempUri(bitmap: Bitmap): Uri? {
        return try {
            val tempFile = File.createTempFile("temp_image", ".png", requireContext().cacheDir)
            val outputStream = FileOutputStream(tempFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.close()
            Uri.fromFile(tempFile)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        imageClassifierHelper.close()
    }
}
