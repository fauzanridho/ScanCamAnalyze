package com.capstone.scancamanalyze.ui.camera

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.scancamanalyze.data.api.AnalyzeResponse
import com.capstone.scancamanalyze.data.repository.UserRepository
import kotlinx.coroutines.launch
import java.io.File

class CameraViewModel(private val repository: UserRepository) : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is camera Fragment"
    }
    val text: LiveData<String> = _text

    private val _imageUri = MutableLiveData<Uri?>()
    val imageUri: LiveData<Uri?> get() = _imageUri

    private val _level = MutableLiveData<Int?>()
    val level: LiveData<Int?> get() = _level

    fun setImageUri(uri: Uri) {
        _imageUri.value = uri
    }

    fun uploadImage(file: File) {
        viewModelScope.launch {
            val response: AnalyzeResponse? = repository.uploadImage(file)

            if (response != null) {
                _text.value = response.description ?: "No description"
                _level.value = response.level ?: -1
                Log.d(
                    "CameraViewModel",
                    "Upload successful: ${response.description}, Level: ${response.level}"
                )
            } else {
                _text.value = "Error uploading image"
                Log.e("CameraViewModel", "Upload failed")
            }
        }
    }

    fun saveAnalyzeData(imageName: String, level: Int, predictionResult: String) {
        viewModelScope.launch {
            try {
                repository.saveAnalyzeData(imageName, level, predictionResult)
                Log.d(
                    "CameraViewModel",
                    "Data saved: $imageName, Level: $level, Result: $predictionResult"
                )
            } catch (e: Exception) {
                Log.e("CameraViewModel", "Error saving data: ${e.message}")
            }
        }
    }
}