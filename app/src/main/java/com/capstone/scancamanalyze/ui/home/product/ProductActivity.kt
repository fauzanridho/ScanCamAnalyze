package com.capstone.scancamanalyze.ui.home.product

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.capstone.scancamanalyze.R
import com.capstone.scancamanalyze.databinding.ActivityProductBinding


class ProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductBinding
    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

//         Mendapatkan nama kategori product dari Intent
        val productName = intent.getStringExtra("PRODUCT_NAME") ?: "Product"

//         Menampilkan nama kategori di bagian judul
        binding.tvCategoryTitle.text = productName

        // Setup RecyclerView
        setupRecyclerView()

        // Tombol kembali
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupRecyclerView() {

        val allProducts = listOf(
            Product("Facewash A", "Brand X", "Deskripsi facewash", "Rp 50.000", R.drawable.sabuncucimuka),
            Product("Facewash B", "Brand Y", "Deskripsi facewash", "Rp 60.000", R.drawable.sabuncucimuka),
            Product("Toner A", "Brand Z", "Deskripsi toner", "Rp 70.000", R.drawable.toner),
            Product("Serum A", "Brand W", "Deskripsi serum", "Rp 80.000", R.drawable.serum),
            Product("Sunscreen A", "Brand V", "Deskripsi sunscreen", "Rp 90.000", R.drawable.sunscreen)
        )

        productAdapter = ProductAdapter(allProducts)
        binding.rvProducts.layoutManager = GridLayoutManager(this, 2)
        binding.rvProducts.adapter = productAdapter
    }


}
