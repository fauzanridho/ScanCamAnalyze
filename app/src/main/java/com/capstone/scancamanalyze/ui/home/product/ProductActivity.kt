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
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productName =
            intent.getStringExtra("CATEGORY_NAME")?.lowercase(Locale.ROOT) ?: "Product"
        binding.tvCategoryTitle.text = productName

        productViewModel.fetchProduct(productName)

        observeViewModel()

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun observeViewModel() {
        productViewModel.productData.observe(this, Observer { products ->
            products?.let {
                val product = it.files?.filterNotNull() ?: emptyList()

                val combinedData = combineData(product)
                setupRecyclerView(combinedData)
            }
        })

        productViewModel.errorMessage.observe(this, Observer { errorMessage ->
            errorMessage?.let {
                showErrorMessage(it)
            }
        })
    }

    private fun combineData(files: List<FilesItem>): List<FilesItem> {
        val combinedData = mutableListOf<FilesItem>()

        for (i in files.indices step 2) {
            val imageFile = files[i]
            val textFile = files.getOrNull(i + 1)

            if (imageFile != null) {
                textFile?.url?.let { url ->
                    productViewModel.fetchText(url) { descriptionText ->
                        val combinedItem = FilesItem(
                            name = textFile.name ?: "No Name",
                            type = "image",
                            url = imageFile.url,
                            description = descriptionText
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
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun setupRecyclerView(products: List<FilesItem>) {
        productAdapter = ProductAdapter(products) { product ->
            val intent = Intent(this, DetailProduct::class.java).apply {
                putExtra("EXTRA_PRODUCT_NAME", product.name)
                putExtra("EXTRA_PRODUCT_TYPE", product.type)
                putExtra("EXTRA_PRODUCT_URL", product.url)
            }
            startActivity(intent)
        }

        binding.rvProducts.layoutManager = GridLayoutManager(this, 2)
        binding.rvProducts.adapter = productAdapter

        supportActionBar?.hide()
    }
}