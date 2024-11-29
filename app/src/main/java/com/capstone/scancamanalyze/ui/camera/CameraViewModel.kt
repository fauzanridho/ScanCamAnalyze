package com.capstone.scancamanalyze.ui.camera

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.scancamanalyze.data.UserRepository
import kotlinx.coroutines.launch

class CameraViewModel(private val repository: UserRepository) : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is camera Fragment"
    }
    val text: LiveData<String> = _text

    private val _imageUri = MutableLiveData<Uri?>()
    val imageUri: LiveData<Uri?> get() = _imageUri

    fun setImageUri(uri: Uri) {
        _imageUri.value = uri
    }

    fun saveAnalyzeData(imageUri: Uri, level: Int, predictionResult: String) {
        // Convert Uri to String
        val imageUriString = imageUri.toString()

        // Save the analyze data using the repository
        viewModelScope.launch {
            repository.saveAnalyzeData(imageUriString, level, predictionResult)
        }

        Log.d("CameraViewModel", "Analyze data saved: $imageUriString, $level, $predictionResult")
    }
}
