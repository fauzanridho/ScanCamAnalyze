package com.capstone.scancamanalyze.ui.profile

import android.content.Context
import android.content.res.Configuration
import android.os.LocaleList
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.scancamanalyze.data.pref.UserModel
import com.capstone.scancamanalyze.data.UserRepository
import kotlinx.coroutines.launch
import java.util.Locale

class ProfileViewModel(private val repository: UserRepository, private val dataStoreManager: DataStoreManager) : ViewModel() {

    fun changeLanguage(context: Context, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocales(LocaleList(locale))
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }

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