package com.capstone.scancamanalyze.ui.camera

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.scancamanalyze.data.UserRepository
import com.capstone.scancamanalyze.data.local.AnalyzeDatabase
import kotlinx.coroutines.launch

class CameraViewModel(private val repository: UserRepository) : ViewModel() {


    private val _text = MutableLiveData<String>().apply {
        value = "This is camera Fragment"
    }
    val text: LiveData<String> = _text

    var imageUri: Uri? = null
    fun saveAnalyzeData(imageName: String, level: Int, predictionResult: String) {
        viewModelScope.launch {
            repository.saveAnalyzeData(imageName, level, predictionResult)
        }
        Log.d("CameraViewModel", "Analyze data saved: $imageName, $level, $predictionResult")
    }
}
