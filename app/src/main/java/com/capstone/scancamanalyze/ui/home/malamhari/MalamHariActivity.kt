package com.capstone.scancamanalyze.ui.home.malamhari

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.scancamanalyze.R
import com.capstone.scancamanalyze.adapter.RecommendationAdapter
import com.capstone.scancamanalyze.databinding.ActivityMalamHariBinding
import com.capstone.scancamanalyze.ui.home.product.Product

class MalamHariActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMalamHariBinding
    private lateinit var adapter: RecommendationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMalamHariBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Contoh data produk
        val products = listOf(
            Product("Cleanser Malam", "Brand X", "Membersihkan wajah sebelum tidur", "Rp 60.000", R.drawable.sunscreen),
            Product("Night Cream", "Brand Y", "Perawatan wajah malam hari", "Rp 150.000", R.drawable.sabuncucimuka)
        )

        adapter = RecommendationAdapter(products)
        binding.recyclerViewRecommendations.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewRecommendations.adapter = adapter
    }
}
