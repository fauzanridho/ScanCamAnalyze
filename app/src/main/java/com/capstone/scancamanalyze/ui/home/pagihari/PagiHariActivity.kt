package com.capstone.scancamanalyze.ui.home.pagihari

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.capstone.scancamanalyze.adapter.ProductAdapter
import com.capstone.scancamanalyze.data.allProducts
import com.capstone.scancamanalyze.databinding.ActivityPagiHariBinding
import com.capstone.scancamanalyze.ui.detail.product.DetailProduct


class PagiHariActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPagiHariBinding
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPagiHariBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Memfilter produk yang kategori-nya "pagi & malam"
        val filteredProducts = allProducts.filter { it.kategori == "pagi & malam" || it.kategori == "pagi" }

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