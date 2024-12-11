package com.capstone.scancamanalyze.data.api

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("files")
    fun getFiles(): Call<Response>

}