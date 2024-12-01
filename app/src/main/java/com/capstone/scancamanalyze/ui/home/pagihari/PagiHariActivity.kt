package com.capstone.scancamanalyze.ui.home.pagihari

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.scancamanalyze.R
import com.capstone.scancamanalyze.adapter.RecommendationAdapter
import com.capstone.scancamanalyze.databinding.ActivityLoginBinding
import com.capstone.scancamanalyze.databinding.ActivityPagiHariBinding
import com.capstone.scancamanalyze.ui.home.product.Product

class PagiHariActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPagiHariBinding
    private lateinit var adapter: RecommendationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPagiHariBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_pagi_hari)

        val products = listOf(
            Product("Cleanser Malam", "Brand X", "Membersihkan wajah sebelum tidur", "Rp 60.000", R.drawable.sabuncucimuka),
            Product("Night Cream", "Brand Y", "Perawatan wajah pagi hari", "Rp 150.000", R.drawable.serum)
        )
        adapter = RecommendationAdapter(products)
        binding.recyclerViewRecommendations.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewRecommendations.adapter = adapter
    }
}