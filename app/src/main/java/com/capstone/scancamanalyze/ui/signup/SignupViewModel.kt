package com.capstone.scancamanalyze.ui.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.scancamanalyze.data.repository.UserRepository
import com.capstone.scancamanalyze.data.request.RegisterRequest
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class SignUpViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun register(registerRequest: RegisterRequest, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                if (registerRequest.name.isBlank() || registerRequest.email.isBlank() || registerRequest.password.isBlank()) {
                    onError("Please fill in all fields")
                    return@launch
                }

                // Call register() on the userRepository instance
                val response = userRepository.register(registerRequest)

                Log.d("SignUpViewModel", "Registration response: $response") // Log the response

                if (response.message == "User created successfully") { // Check for success message
                    onSuccess()
                } else {
                    onError(response.message ?: "Registration failed")
                }
            } catch (e: HttpException) {
                Log.e("SignUpViewModel", "Network error: ${e.localizedMessage}", e) // Log the error
                onError("Network error: ${e.localizedMessage}")
            } catch (e: IOException) {
                Log.e("SignUpViewModel", "Connection error: ${e.localizedMessage}", e) // Log the error
                onError("Connection error: ${e.localizedMessage}")
            } catch (e: Exception) {
                Log.e("SignUpViewModel", "An unexpected error occurred: ${e.localizedMessage}", e) // Log the error
                onError("An unexpected error occurred: ${e.localizedMessage}")
            }
        }
    }
}
