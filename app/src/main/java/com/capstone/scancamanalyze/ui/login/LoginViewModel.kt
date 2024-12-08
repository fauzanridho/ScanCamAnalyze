package com.capstone.scancamanalyze.ui.login

import androidx.lifecycle.*
import com.capstone.scancamanalyze.data.repository.UserRepository
import com.capstone.scancamanalyze.data.pref.UserModel
import com.capstone.scancamanalyze.data.request.LoginRequest
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _session = MutableLiveData<UserModel>()
    val session: LiveData<UserModel> get() = _session

    fun login(loginRequest: LoginRequest, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = userRepository.login(loginRequest)
                if (response.token.isNullOrEmpty()) {
                    response.message?.let { onError(it) }
                    return@launch
                }

                val user = UserModel(
                    email = loginRequest.email,
                    token = response.token,
                    isLogin = true
                )
                userRepository.saveSession(user)
                _session.value = user
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "An error occurred during login")
            }
        }
    }

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            userRepository.saveSession(user)
        }
    }
}
