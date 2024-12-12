package com.capstone.scancamanalyze.ui.home.product

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.capstone.scancamanalyze.ViewModelFactory
import com.capstone.scancamanalyze.adapter.ProductAdapter
import com.capstone.scancamanalyze.data.api.FilesItem
import com.capstone.scancamanalyze.databinding.ActivityProductBinding
import com.capstone.scancamanalyze.ui.detail.product.DetailProduct
import java.util.Locale

class ProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductBinding
    private lateinit var productAdapter: ProductAdapter
    private val productViewModel by viewModels<ProductViewModel> {
        ViewModelFactory.getInstance(this)
    } // Using the ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mendapatkan nama kategori produk dari Intent
        val productName =
            intent.getStringExtra("CATEGORY_NAME")?.lowercase(Locale.ROOT) ?: "Product"
        binding.tvCategoryTitle.text = productName

        // Fetch sunscreen data using ViewModel
        productViewModel.fetchProduct(productName)

        // Observe the LiveData from the ViewModel
        observeViewModel()

        // Tombol kembali
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun observeViewModel() {
        // Observe the sunscreen data
        productViewModel.productData.observe(this, Observer { products ->
            products?.let {
                val product = it.files?.filterNotNull() ?: emptyList()

                // Menggabungkan data gambar dan teks sesuai urutan
                val combinedData = combineData(product)
                setupRecyclerView(combinedData)
            }
        })

        // Observe error message
        productViewModel.errorMessage.observe(this, Observer { errorMessage ->
            errorMessage?.let {
                // Handle the error (e.g., show a Toast or Snackbar)
                showErrorMessage(it)
            }
        })
    }

    private fun combineData(files: List<FilesItem>): List<FilesItem> {
        val combinedData = mutableListOf<FilesItem>()

        // Gunakan coroutine untuk menjalankan operasi jaringan secara asynchronous
        for (i in files.indices step 2) {
            val imageFile = files[i]
            val textFile = files.getOrNull(i + 1)

            if (imageFile != null) {
                // Memanggil ViewModel untuk mengambil teks secara asynchronous
                textFile?.url?.let { url ->
                    productViewModel.fetchText(url) { descriptionText ->
                        // Gabungkan gambar dan deskripsi setelah mengambil teks
                        val combinedItem = FilesItem(
                            name = textFile.name ?: "No Name",
                            type = "image", // Tipe gambar
                            url = imageFile.url, // URL gambar
                            description = descriptionText // Deskripsi dari file teks
                        )
                        combinedData.add(combinedItem)
                    }
                } ?: run {
                    val combinedItem = FilesItem(
                        name = textFile?.name ?: "No Name",
                        type = "image",
                        url = imageFile.url,
                        description = "No Description"
                    )
                    combinedData.add(combinedItem)
                }
            }
        }

        return combinedData
    }

    private fun showErrorMessage(message: String) {
        // You can show an error message to the user, e.g., using a Toast or Snackbar
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun setupRecyclerView(products: List<FilesItem>) {
        // Setup the RecyclerView with the adapter
        productAdapter = ProductAdapter(products) { product ->
            val intent = Intent(this, DetailProduct::class.java).apply {
                putExtra("EXTRA_PRODUCT_NAME", product.name)
                putExtra("EXTRA_PRODUCT_TYPE", product.type)  // Update based on the data fields
                putExtra("EXTRA_PRODUCT_URL", product.url)   // Pass the URL for the image
            }
            startActivity(intent)
        }

        binding.rvProducts.layoutManager = GridLayoutManager(this, 2)
        binding.rvProducts.adapter = productAdapter

        supportActionBar?.hide()
    }
}