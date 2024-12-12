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

    // Ganti bagian ini untuk menggunakan file chooser
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
        setupObservers()
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

        // Ubah button gallery untuk meluncurkan file chooser
        binding.buttonGallery.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "image/*" // Ganti sesuai jenis file yang diinginkan
            }
            pickImageLauncher.launch(intent)
        }

        binding.buttonAnalyze.setOnClickListener {
            viewModel.imageUri.value?.let { uri ->
                val imageFile = uriToFile(uri, requireContext())
                viewModel.uploadImage(imageFile)
                Log.d("CameraFragment", "Analyzing image...")
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
