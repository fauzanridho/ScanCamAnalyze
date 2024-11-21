package com.capstone.scancamanalyze.data.api

import retrofit2.http.GET

interface ApiService {
    @GET("events")
    suspend fun getAllEvents() : EventResponse
}