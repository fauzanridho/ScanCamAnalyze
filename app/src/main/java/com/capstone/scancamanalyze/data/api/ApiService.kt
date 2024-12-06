package com.capstone.scancamanalyze.data.api

import retrofit2.http.GET

interface ApiService {
    @GET("toner")
    suspend fun getToner(): Toner
}