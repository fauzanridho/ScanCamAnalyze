package com.capstone.scancamanalyze.data.repository

import com.capstone.scancamanalyze.data.api.ApiConfig
import com.capstone.scancamanalyze.data.local.AnalyzeDao
import com.capstone.scancamanalyze.data.local.AnalyzeEntity
import com.capstone.scancamanalyze.data.pref.UserModel
import com.capstone.scancamanalyze.data.pref.UserPreference
import com.capstone.scancamanalyze.data.request.LoginRequest
import com.capstone.scancamanalyze.data.request.RegisterRequest
import kotlinx.coroutines.flow.Flow

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val analyzeDao: AnalyzeDao
) {
    private val apiService = ApiConfig.getApiService()

    suspend fun register(registerRequest: RegisterRequest) =
        apiService.register(
            registerRequest.name,
            registerRequest.email,
            registerRequest.password
        )

    suspend fun login(loginRequest: LoginRequest) =
        apiService.logInUser(loginRequest.email, loginRequest.password)

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun saveAnalyzeData(
        imageName: String,
        level: Int,
        predictionResult: String
    ) {
        val analyzeEntity = AnalyzeEntity(
            imageName = imageName,
            level = level,
            predictionResult = predictionResult
        )
        analyzeDao.insertAnalyze(analyzeEntity)
    }
    suspend fun getAllAnalyzes(): List<AnalyzeEntity> {
        return analyzeDao.getAllAnalyzes()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            analyzeDao: AnalyzeDao
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference,analyzeDao)
            }.also { instance = it }
    }
}
