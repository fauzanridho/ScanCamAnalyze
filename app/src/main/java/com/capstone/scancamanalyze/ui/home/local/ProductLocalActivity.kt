package com.capstone.scancamanalyze.ui.home.local

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.scancamanalyze.R
import com.capstone.scancamanalyze.ViewModelFactory
import com.capstone.scancamanalyze.adapter.ProductLocalAdapter
import com.capstone.scancamanalyze.data.local.ProductEntity
import com.capstone.scancamanalyze.databinding.ActivityProductLocalBinding
import java.util.Locale

class ProductLocalActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductLocalBinding
    private val viewModel by viewModels<ProductLocalViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var productLocalAdapter: ProductLocalAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityProductLocalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categoryName =
            intent.getStringExtra("CATEGORY_NAME")?.lowercase(Locale.ROOT) ?: "Product"
        // Inisialisasi adapter
        viewModel.getProductsByCategory(categoryName)
        productLocalAdapter = ProductLocalAdapter()

        // Set up RecyclerView
        binding.rvProducts.apply {
            layoutManager = LinearLayoutManager(this@ProductLocalActivity)
            adapter = productLocalAdapter
        }
        binding.tvCategoryTitle.text = categoryName

        // Observasi data produk dari ViewModel
        viewModel.products.observe(this, Observer { products ->
            // Memperbarui list produk di adapter
            productLocalAdapter.submitList(products)
        })

        // Memanggil ViewModel untuk mengambil produk

        viewModel.products.observe(this, Observer { products ->
            // Memperbarui list produk di adapter
            productLocalAdapter.submitList(products)
        })

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        binding.btnAddProduct.setOnClickListener {
            showAddProductDialog()
        }
        supportActionBar?.hide()
    }

    private fun showAddProductDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_product, null)
        val etProductName: EditText = dialogView.findViewById(R.id.etProductName)
        val etProductDescription: EditText = dialogView.findViewById(R.id.etProductDescription)
        val etProductCategory: EditText = dialogView.findViewById(R.id.etProductCategory)
        val etProductImageUrl: EditText = dialogView.findViewById(R.id.etProductImageUrl)
        val etProductPrice: EditText = dialogView.findViewById(R.id.etProductPrice)

        val builder = AlertDialog.Builder(this)
            .setTitle("Tambah Produk")
            .setView(dialogView)
            .setPositiveButton("Simpan") { _, _ ->
                val productName = etProductName.text.toString()
                val productDescription = etProductDescription.text.toString()
                val productCategory = etProductCategory.text.toString()
                val productImageUrl = etProductImageUrl.text.toString()
                val productPriceString = etProductPrice.text.toString()
                val productPrice = productPriceString.toDoubleOrNull() ?: 0.0

                if (productName.isNotEmpty() && productDescription.isNotEmpty() &&
                    productCategory.isNotEmpty() && productImageUrl.isNotEmpty()
                ) {
                    val newProduct = ProductEntity(
                        id = 0, // ID otomatis jika menggunakan Room
                        productName = productName,
                        description = productDescription,
                        category = productCategory,
                        imageUrl = productImageUrl,
                        price = productPrice// Sesuaikan dengan kebutuhan Anda
                    )
                    viewModel.insertProduct(newProduct)
                } else {
                    Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Batal", null)

        builder.show()
    }
}