package com.capstone.scancamanalyze.data.repository

import android.util.Log
import com.capstone.scancamanalyze.data.api.AnalyzeResponse
import com.capstone.scancamanalyze.data.api.ApiConfig
import com.capstone.scancamanalyze.data.api.ApiConfigAnalyze
import com.capstone.scancamanalyze.data.api.ApiService
import com.capstone.scancamanalyze.data.api.ApiServiceAnalyze
import com.capstone.scancamanalyze.data.api.Product
import com.capstone.scancamanalyze.data.local.AnalyzeDao
import com.capstone.scancamanalyze.data.local.AnalyzeEntity
import com.capstone.scancamanalyze.data.pref.UserModel
import com.capstone.scancamanalyze.data.pref.UserPreference
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.Response
import java.io.File

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val analyzeDao: AnalyzeDao
) {
    private val auth: FirebaseAuth = Firebase.auth
    private val apiService: ApiService = ApiConfig.getApiService()
    private val apiServiceAnalyze: ApiServiceAnalyze = ApiConfigAnalyze.getApiService()
    private val client = OkHttpClient()

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        auth.signOut()
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

    suspend fun getProduct(waktu: String, produk: String): Product? {
        val response = apiService.getProduct(waktu, produk)
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun getallmalam(): Product? {
        val response = apiService.getallmalam()
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun getallpagi(): Product? {
        val response = apiService.getallpagi()
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun uploadImage(filePath: File): AnalyzeResponse? {
        val file = filePath
        val requestBody: RequestBody = file.asRequestBody("image/jpeg".toMediaType())
        val filePart: MultipartBody.Part =
            MultipartBody.Part.createFormData("file", file.name, requestBody)

        try {
            val response = apiServiceAnalyze.uploadImage(filePart)

            Log.d("UserRepository", "file path: $filePath")

            if (response.isSuccessful) {
                val responseBody = response.body()
                return responseBody?.let {
                    AnalyzeResponse(
                        description = it.description,
                        level = it.level
                    )
                }
            } else {
                Log.e("UserRepository", "Error uploading image: ${response.message()}")
                return null
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Exception while uploading image: ${e.message}")
            return null
        }
    }

    suspend fun fetchText(url: String): String {
        val request = Request.Builder().url(url).build()

        return withContext(Dispatchers.IO) {
            try {
                val response: Response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    response.body?.string() ?: "No Content"
                } else {
                    "Failed to fetch content"
                }
            } catch (e: Exception) {
                "Error: ${e.message}"
            }
        }
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            analyzeDao: AnalyzeDao
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, analyzeDao)
            }.also { instance = it }
    }
}