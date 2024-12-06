package com.capstone.scancamanalyze.ui.home.malamhari

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.scancamanalyze.R
import com.capstone.scancamanalyze.adapter.ProductAdapter
import com.capstone.scancamanalyze.adapter.RecommendationAdapter
import com.capstone.scancamanalyze.data.Product
import com.capstone.scancamanalyze.data.allProducts

import com.capstone.scancamanalyze.databinding.ActivityMalamHariBinding
import com.capstone.scancamanalyze.ui.detail.product.DetailProduct

class MalamHariActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMalamHariBinding
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMalamHariBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Memfilter produk yang kategori-nya "pagi & malam"
        val filteredProducts = allProducts.filter { it.kategori == "pagi & malam" || it.kategori == "malam" }

        adapter = ProductAdapter(filteredProducts) { product ->
            val intent = Intent(this, DetailProduct::class.java).apply {
                putExtra("EXTRA_PRODUCT_NAME", product.nama)
                putExtra("EXTRA_PRODUCT_DETAIL", product.detail)
                putExtra("EXTRA_PRODUCT_IMAGE", product.image)
                putExtra("EXTRA_PRODUCT_KATEGORI", product.kategori)
            }
            startActivity(intent)
        }
        binding.recyclerViewRecommendations.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerViewRecommendations.adapter = adapter

        supportActionBar?.hide()
    }
}
