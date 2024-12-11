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
import com.capstone.scancamanalyze.ml.ImageClassifierHelper

import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class CameraFragment : Fragment() {

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!

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
                    val tempUri = bitmapToTempUri(it)
                    tempUri?.let { uri ->
                        viewModel.setImageUri(uri)  // Simpan imageUri ke ViewModel
                    }
                }
            }
        }

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data
                uri?.let {
                    binding.imagePreview.setImageURI(it)
                    viewModel.setImageUri(it)  // Simpan imageUri ke ViewModel
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
            maxResults = 4,
            context = requireContext(),
            classifierListener = object : ImageClassifierHelper.ClassifierListener {
                override fun onError(error: String) {
                    Log.e("ImageClassifier", error)
                }

                override fun onResults(results: List<ImageClassifierHelper.Classifications>) {
                    displayResults(results)
                }
            }
        )
    }

    private fun setupObservers() {
        viewModel.text.observe(viewLifecycleOwner) {
            binding.resultText.text = it
        }

        viewModel.imageUri.observe(viewLifecycleOwner) { uri ->
            uri?.let {
                binding.imagePreview.setImageURI(it)  // Update preview jika imageUri berubah
            }
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
            viewModel.imageUri.value?.let { uri ->
                classifyImageFromUri(uri)
            } ?: run {
                Log.e("CameraFragment", "No image selected")
            }
        }
    }

    private fun classifyImageFromUri(uri: Uri) {
        imageClassifierHelper.classifyStaticImage(uri)
    }

    private fun displayResults(results: List<ImageClassifierHelper.Classifications>) {
        if (results.isEmpty()) {
            binding.resultText.text = "No results found"
            Log.e("CameraFragment", "No classification results")
            return
        }

        // Formatkan hasil menjadi string
        val resultText = results.joinToString(separator = "\n") { "${it.label}: ${"%.2f".format(it.score * 100)}%" }
        binding.resultText.text = resultText

        // Ambil data klasifikasi terbaik
        val bestResult = results.maxByOrNull { it.score }
        val level = (bestResult?.score ?: 0f) * 100 // Mengonversi ke persentase
        val predictionResult = bestResult?.label ?: "Unknown"

        // Simpan hasil ke ViewModel
        viewModel.imageUri.value?.let { uri ->
            viewModel.saveAnalyzeData(uri, level.toInt(), predictionResult.toString())
        } ?: Log.e("CameraFragment", "No imageUri available for saving results")
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