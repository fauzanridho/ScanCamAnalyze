package com.capstone.scancamanalyze.ui.camera

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.capstone.scancamanalyze.uriToFile
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class CameraFragment : Fragment() {

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!
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
                        viewModel.setImageUri(uri)
                    }
                }
            }
        }

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data
                uri?.let {
                    binding.imagePreview.setImageURI(it)
                    val fileName = getFileNameFromUri(it)
                    Log.d("CameraFragment", "Selected file: $fileName")
                    viewModel.setImageUri(it)
                }
            } else {
                Log.e("CameraFragment", "No image selected")
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        setupObserversImage()
        setupListeners()
        return binding.root
    }

    private fun setupObservers() {
        viewModel.text.observe(viewLifecycleOwner) { description ->
            binding.resultText.text = description
            checkAndSaveData()
        }

        viewModel.level.observe(viewLifecycleOwner) { level ->
            binding.levelTitle.text = "Level: $level"
            checkAndSaveData()
        }


    }

    private fun setupObserversImage() {
        viewModel.imageUri.observe(viewLifecycleOwner) { uri ->
            uri?.let {
                binding.imagePreview.setImageURI(it)
            }
        }
    }

    private fun setupListeners() {
        binding.buttonCamera.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraLauncher.launch(cameraIntent)
        }

        binding.buttonGallery.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "image/*"
            }
            pickImageLauncher.launch(intent)
        }

        binding.buttonAnalyze.setOnClickListener {
            viewModel.imageUri.value?.let { uri ->
                val imageFile = uriToFile(uri, requireContext())

                binding.progressBar.visibility = View.VISIBLE
                viewModel.uploadImage(imageFile)

                Handler(Looper.getMainLooper()).postDelayed({
                    binding.progressBar.visibility = View.GONE
                    setupObservers()
                    Log.d("CameraFragment", "Analyzing image...")
                }, 5000)
            } ?: run {
                Log.e("CameraFragment", "No image selected")
            }
        }
    }

    private fun checkAndSaveData() {
        val imageUri = viewModel.imageUri.value
        val fileName = getFileNameFromUri(imageUri)
        val level = viewModel.level.value
        val predictionResult = viewModel.text.value

        if (fileName != null && level != null && predictionResult != null) {
            viewModel.saveAnalyzeData(imageUri.toString(), level, predictionResult)
            Log.d("CameraFragment", "Data saved successfully")
        } else {
            Log.e(
                "CameraFragment",
                "Invalid data: Missing fields - fileName=$fileName, level=$level, predictionResult=$predictionResult"
            )
        }
    }

    private fun getFileNameFromUri(uri: Uri?): String? {
        uri ?: return null
        val cursor = requireContext().contentResolver.query(
            uri, arrayOf(MediaStore.Images.Media.DISPLAY_NAME),
            null, null, null
        )
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
                return it.getString(nameIndex)
            }
        }
        return null
    }

    private fun bitmapToTempUri(bitmap: Bitmap): Uri? {
        return try {
            val tempFile = File.createTempFile("temp_image", ".jpg", requireContext().cacheDir)
            FileOutputStream(tempFile).use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            }
            Uri.fromFile(tempFile)
        } catch (e: IOException) {
            Log.e("CameraFragment", "Error saving image: ${e.message}")
            null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}