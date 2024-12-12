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
        // Upload image using the repository
        viewModelScope.launch {
            val response: AnalyzeResponse? = repository.uploadImage(file)

            // Handle the response
            if (response != null) {
                // Update the LiveData with the description and level
                _text.value = response.description
                _level.value = response.level
                // Log the level
                Log.d(
                    "CameraViewModel",
                    "Level: ${response.level}, Description: ${response.description}"
                )
            } else {
                _text.value = "Error uploading image"
            }
        }
    }
}
