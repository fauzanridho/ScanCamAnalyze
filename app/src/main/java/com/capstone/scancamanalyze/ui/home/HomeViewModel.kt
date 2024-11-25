package com.capstone.scancamanalyze.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.scancamanalyze.data.pref.UserModel
import com.capstone.scancamanalyze.data.UserRepository
import com.capstone.scancamanalyze.data.api.ApiConfig
import com.capstone.scancamanalyze.data.api.EventResponse
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: UserRepository) : ViewModel() {

    private val _articleList = MutableLiveData<EventResponse>()
    val articleList: LiveData<EventResponse> = _articleList

    fun getStories() {
        viewModelScope.launch {
            repository.getSession().collect { user ->
                try {
                    val response = ApiConfig.getApiService().getAllEvents()
                    _articleList.value = response
                } catch (e: Exception) {
                    _articleList.value = EventResponse(error = true, message = e.message)
                }
            }
        }
    }
    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}