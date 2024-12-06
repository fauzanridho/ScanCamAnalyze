package com.capstone.scancamanalyze.data.api

import com.capstone.scancamanalyze.data.Product
import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("token")
    val token: String? = null
)

data class RegisterResponse(

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("userId")
    val userId: String? = null
)

data class Toner(

    @field:SerializedName("ItemToner")
    val itemToner: List<ProductItem?>? = null
)

data class ProductItem(

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("kategori")
    val kategori: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("detail")
    val detail: String? = null
)