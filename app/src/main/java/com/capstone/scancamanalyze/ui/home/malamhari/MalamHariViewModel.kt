package com.capstone.scancamanalyze.ui.home.malamhari

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.scancamanalyze.data.api.FilesItem
import com.capstone.scancamanalyze.data.api.Product
import com.capstone.scancamanalyze.data.repository.UserRepository
import kotlinx.coroutines.launch

class MalamHariViewModel(private val repository: UserRepository) : ViewModel() {
    private val _productData = MutableLiveData<Product?>()
    val productData: LiveData<Product?> = _productData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun fetchProduct() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val pagiResponse = repository.getProduct("pagi", "toner")
                val malamResponse = repository.getProduct("malam", "pelembab")

                val combinedFiles = mutableListOf<FilesItem>()
                pagiResponse?.files?.let {
                    combinedFiles.addAll(it.filterNotNull())
                    Log.d("ProductViewModel", "Pagi files: ${it.size}")
                }
                malamResponse?.files?.let {
                    combinedFiles.addAll(it.filterNotNull())
                    Log.d("ProductViewModel", "Malam files: ${it.size}")
                }

                if (combinedFiles.isEmpty()) {
                    _errorMessage.value = "Failed to load data."
                } else {
                    _productData.value = Product(files = combinedFiles)
                    _errorMessage.value = null
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "An unexpected error occurred."
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchText(url: String, onResult: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val text = repository.fetchText(url)  // Memanggil repository untuk ambil teks
                onResult(text)
            } catch (e: Exception) {
                onResult("Error: ${e.message}")
            }
        }
    }
}