//package com.capstone.scancamanalyze.ui.home.produk
//
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.capstone.scancamanalyze.R
//import com.capstone.scancamanalyze.databinding.ActivityProdukBinding
//
//class ProdukActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityProdukBinding
////    private lateinit var produkAdapter: ProdukAdapter
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityProdukBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        // Mendapatkan nama kategori produk dari Intent
//        val productName = intent.getStringExtra("PRODUCT_NAME") ?: "Produk"
//
//        // Menampilkan nama kategori di bagian judul
//        binding.tvCategoryTitle.text = productName
//
////        // Setup RecyclerView
////        setupRecyclerView(productName)
//
//        // Tombol kembali
//        binding.btnBack.setOnClickListener {
//            onBackPressed()
//        }
//    }
//
////    private fun setupRecyclerView(categoryName: String) {
////        produkAdapter = ProdukAdapter(getProductsByCategory(categoryName)) // Filter produk berdasarkan kategori
////        binding.rvProducts.layoutManager = LinearLayoutManager(this)
////        binding.rvProducts.adapter = produkAdapter
////    }
//
//    private fun getProductsByCategory(categoryName: String): List<Produk> {
//        // Data contoh: ganti dengan data dari database atau API
//        val allProducts = listOf(
//            Produk("Facewash A", "Brand X", "Deskripsi facewash", "Rp 50.000", R.drawable.sabuncucimuka),
//            Produk("Facewash B", "Brand Y", "Deskripsi facewash", "Rp 60.000", R.drawable.sabuncucimuka),
//            Produk("Toner A", "Brand Z", "Deskripsi toner", "Rp 70.000", R.drawable.toner),
//            Produk("Serum A", "Brand W", "Deskripsi serum", "Rp 80.000", R.drawable.serum),
//            Produk("Sunscreen A", "Brand V", "Deskripsi sunscreen", "Rp 90.000", R.drawable.sunscreen)
//        )
//
//        // Filter produk berdasarkan kategori
//        return allProducts.filter { it.name.contains(categoryName, ignoreCase = true) }
//    }
//}
