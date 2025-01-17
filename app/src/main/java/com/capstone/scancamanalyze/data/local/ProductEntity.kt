package com.capstone.scancamanalyze.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productName: String,     // Nama produk
    val description: String,     // Deskripsi produk
    val category: String,        // Kategori produk
    val imageUrl: String,        // URL atau path gambar produk
    val price: Double            // Harga produk
)