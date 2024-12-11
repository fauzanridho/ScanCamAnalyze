package com.capstone.scancamanalyze.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.scancamanalyze.data.pref.UserModel
import com.capstone.scancamanalyze.data.repository.UserRepository
import com.capstone.scancamanalyze.data.local.AnalyzeEntity
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: UserRepository) : ViewModel() {

    private val _analyzeList = MutableLiveData<List<AnalyzeEntity>>()
    val analyzeList: LiveData<List<AnalyzeEntity>> get() = _analyzeList

    fun fetchAnalyzeHistory() {
        viewModelScope.launch {
            _analyzeList.postValue(repository.getAllAnalyzes())
        }
    }
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}
