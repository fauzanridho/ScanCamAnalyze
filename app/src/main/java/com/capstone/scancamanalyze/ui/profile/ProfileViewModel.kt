package com.capstone.scancamanalyze.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.scancamanalyze.data.pref.UserModel
import com.capstone.scancamanalyze.data.pref.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: UserRepository, private val dataStoreManager: DataStoreManager) : ViewModel() {

    fun isDarkMode(): LiveData<Boolean> {
        return dataStoreManager.themeFlow.asLiveData()
    }

    fun setDarkMode(isDarkMode: Boolean) {
        viewModelScope.launch {
            dataStoreManager.setTheme(isDarkMode)
        }
    }


    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

}