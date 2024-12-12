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
import androidx.activity.result.PickVisualMediaRequest
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
                        viewModel.setImageUri(uri)  // Save imageUri to ViewModel
                    }
                }
            }
        }

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                binding.imagePreview.setImageURI(uri)
                viewModel.setImageUri(uri)  // Save imageUri to ViewModel
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

        // Observers for imageUri and text
        setupObservers()

        // Initialize listeners for buttons
        setupListeners()

        // Request permissions to access storage and camera


        return binding.root
    }


    private fun setupObservers() {
        viewModel.text.observe(viewLifecycleOwner) { description ->
            binding.resultText.text = description  // Display the description
        }

        viewModel.level.observe(viewLifecycleOwner) { level ->
            binding.levelTitle.text = "Level: $level"  // Display the level
        }

        viewModel.imageUri.observe(viewLifecycleOwner) { uri ->
            uri?.let {
                binding.imagePreview.setImageURI(it)  // Update preview when imageUri changes
            }
        }
    }

    private fun setupListeners() {
        binding.buttonCamera.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraLauncher.launch(cameraIntent)
        }

        binding.buttonGallery.setOnClickListener {
            pickImageLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.buttonAnalyze.setOnClickListener {
            viewModel.imageUri.value?.let { uri ->
                val imageFile = uriToFile(uri, requireContext())
                viewModel.uploadImage(imageFile)  // Use uploadImage function in the ViewModel
            } ?: run {
                Log.e("CameraFragment", "No image selected")
            }
        }
    }

    // Convert Bitmap to a temporary file URI
    private fun bitmapToTempUri(bitmap: Bitmap): Uri? {
        return try {
            val tempFile = File.createTempFile("temp_image", ".png", requireContext().cacheDir)
            FileOutputStream(tempFile).use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
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
