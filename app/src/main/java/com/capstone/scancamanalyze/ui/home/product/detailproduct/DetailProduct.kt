package com.capstone.scancamanalyze.ui.home.product.detailproduct

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.capstone.scancamanalyze.R
import com.capstone.scancamanalyze.databinding.ActivityDetailProductBinding

class DetailProduct : AppCompatActivity() {

    private lateinit var binding: ActivityDetailProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mendapatkan data dari intent
        val productName = intent.getStringExtra("EXTRA_PRODUCT_NAME")
        val productBrand = intent.getStringExtra("EXTRA_PRODUCT_BRAND")
        val productDescription = intent.getStringExtra("EXTRA_PRODUCT_DESCRIPTION")
        val productPrice = intent.getStringExtra("EXTRA_PRODUCT_PRICE")
        val productImage = intent.getIntExtra("EXTRA_PRODUCT_IMAGE", 0)

        //menampilkan data di layout
        binding.tvProductName.text = productName
        binding.tvProductBrand.text = productBrand
        binding.tvProductDetails.text = productDescription
        binding.tvProductPrice.text = productPrice
        binding.ivProductImage.setImageResource(productImage)
    }
}