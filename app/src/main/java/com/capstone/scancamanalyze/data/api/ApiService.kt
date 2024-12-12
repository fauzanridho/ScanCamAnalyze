package com.capstone.scancamanalyze.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("produkimg/{waktu}/{produk}")
    suspend fun getProduct(
        @Path("waktu") waktu: String,
        @Path("produk") produk: String
    ): Response<Product>

    @GET("allmalam")
    suspend fun getallmalam(
    ): Response<Product>

    @GET("allpagi")
    suspend fun getallpagi(
    ): Response<Product>
}