package com.capstone.scancamanalyze.ui.detail.product

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.capstone.scancamanalyze.databinding.ActivityDetailProductBinding

class DetailProduct : AppCompatActivity() {

    private lateinit var binding: ActivityDetailProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mendapatkan data dari intent
        val productName = intent.getStringExtra("EXTRA_PRODUCT_NAME")
        val productDetail = intent.getStringExtra("EXTRA_PRODUCT_DESCRIPTION")
        val productImage = intent.getStringExtra("EXTRA_PRODUCT_IMAGE")
        val productKategori = intent.getStringExtra("EXTRA_PRODUCT_KATEGORI")

        // Menampilkan data di layout
        binding.tvProductName.text = productName
        binding.tvProductDetails.text = productDetail
        binding.tvProductPrice.text = productKategori

        // Menggunakan Glide untuk memuat gambar dari URL
        Glide.with(this)
            .load(productImage) // Memuat gambar dari URL
            .into(binding.ivProductImage)

        // Menyembunyikan Action Bar
        supportActionBar?.hide()
    }
}
