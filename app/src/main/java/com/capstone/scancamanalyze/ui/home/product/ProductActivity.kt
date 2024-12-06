package com.capstone.scancamanalyze.ui.home.product

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.capstone.scancamanalyze.adapter.ProductAdapter
import com.capstone.scancamanalyze.data.allProducts
import com.capstone.scancamanalyze.data.api.ProductItem
import com.capstone.scancamanalyze.data.pelembab
import com.capstone.scancamanalyze.data.sabunCuciMuka
import com.capstone.scancamanalyze.data.serum
import com.capstone.scancamanalyze.data.sunscreen
import com.capstone.scancamanalyze.data.toner
import com.capstone.scancamanalyze.databinding.ActivityProductBinding
import com.capstone.scancamanalyze.ui.detail.product.DetailProduct


class ProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductBinding
    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

//         Mendapatkan nama kategori product dari Intent
        val productName = intent.getStringExtra("CATEGORY_NAME") ?: "Product"
//         Menampilkan nama kategori di bagian judul
        val products = when (productName) {
            "Pelembab" -> pelembab
            "Toner" -> toner
            "Sunscreen" -> sunscreen
            "Serum" -> serum
            "Sabun Cuci Muka" -> sabunCuciMuka
            else -> allProducts // Default ke semua produk jika kategori tidak dikenali
        }
        binding.tvCategoryTitle.text = productName

        // Setup RecyclerView
        setupRecyclerView(products)

        // Tombol kembali
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupRecyclerView(products: List<ProductItem>) {

        productAdapter = ProductAdapter(products) { product ->
            val intent = Intent(this, DetailProduct::class.java).apply {
                putExtra("EXTRA_PRODUCT_NAME", product.nama)
                putExtra("EXTRA_PRODUCT_DETAIL", product.detail)
                putExtra("EXTRA_PRODUCT_IMAGE", product.image)
                putExtra("EXTRA_PRODUCT_KATEGORI", product.kategori)
            }
            startActivity(intent)
        }
        binding.rvProducts.layoutManager = GridLayoutManager(this, 2)
        binding.rvProducts.adapter = productAdapter

        supportActionBar?.hide()
    }
}
