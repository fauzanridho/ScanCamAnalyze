package com.capstone.scancamanalyze.ui.login

import androidx.lifecycle.*
import com.capstone.scancamanalyze.data.repository.UserRepository
import com.capstone.scancamanalyze.data.pref.UserModel
import com.capstone.scancamanalyze.data.pref.UserPreference
import com.capstone.scancamanalyze.data.request.LoginRequest
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class LoginViewModel(private val userPreference: UserPreference) : ViewModel() {

    private val _session = MutableLiveData<UserModel>()
    val session: LiveData<UserModel> get() = _session

    fun login(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onError(task.exception?.localizedMessage ?: "Login failed")
                }
            }
    }

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            userPreference.saveSession(user)
        }
    }
}
