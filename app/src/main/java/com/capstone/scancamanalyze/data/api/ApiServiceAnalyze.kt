package com.capstone.scancamanalyze.data.api

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiServiceAnalyze {
    @Multipart
    @POST("upload") // Endpoint for uploading the file
    suspend fun uploadImage(
        @Part file: MultipartBody.Part // The file being uploaded
    ): Response<AnalyzeResponse>
}