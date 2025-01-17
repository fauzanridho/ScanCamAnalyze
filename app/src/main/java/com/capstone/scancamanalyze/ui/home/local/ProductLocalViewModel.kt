package com.capstone.scancamanalyze.ui.home.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.scancamanalyze.data.local.ProductEntity
import com.capstone.scancamanalyze.data.repository.UserRepository
import kotlinx.coroutines.launch

class ProductLocalViewModel(private val repository: UserRepository) : ViewModel() {
    private val _products = MutableLiveData<List<ProductEntity>>()
    val products: LiveData<List<ProductEntity>> = _products

    fun getAllProducts() {
        viewModelScope.launch {
            val productList = repository.getAllProducts()
            _products.postValue(productList)
        }
    }

    fun getProductsByCategory(category: String) {
        viewModelScope.launch {
            val filteredProductList = repository.getProductsByCategory(category)
            _products.postValue(filteredProductList)
        }
    }

    fun insertProduct(product: ProductEntity) {
        viewModelScope.launch {
            repository.insertProduct(product)
            // Update produk setelah insert
            getProductsByCategory(product.category)
        }
    }
}