package com.capstone.scancamanalyze.data.api

import com.google.gson.annotations.SerializedName


data class Product(

    @field:SerializedName("files")
    val files: List<FilesItem?>? = null
)

data class FilesItem(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("url")
    val url: String? = null,

    @field:SerializedName("description")
    val description: String? = null
)

data class FilesItemImage(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("url")
    val url: String? = null
)

data class FilesItemText(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("url")
    val url: String? = null
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

data class AvatarItem(

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("id")
    val id: Int? = null
)