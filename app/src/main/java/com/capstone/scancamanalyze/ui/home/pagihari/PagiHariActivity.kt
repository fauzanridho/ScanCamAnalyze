package com.capstone.scancamanalyze.ui.home.pagihari

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.capstone.scancamanalyze.ViewModelFactory
import com.capstone.scancamanalyze.adapter.ProductAdapter
import com.capstone.scancamanalyze.data.api.FilesItem
import com.capstone.scancamanalyze.databinding.ActivityPagiHariBinding
import com.capstone.scancamanalyze.ui.detail.product.DetailProduct


class PagiHariActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPagiHariBinding
    private lateinit var adapter: ProductAdapter
    private val viewModel by viewModels<PagiHariViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPagiHariBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.fetchProduct()

        observeViewModel()

    }

    private fun observeViewModel() {

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.productData.observe(this, Observer { products ->
            products?.let {
                val product = it.files?.filterNotNull() ?: emptyList()

                val combinedData = combineData(product)
                setupRecyclerView(combinedData)
            }
        })

        viewModel.errorMessage.observe(this, Observer { errorMessage ->
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
            if (textFile != null) {

                viewModel.fetchText(textFile.url!!) { description ->
                    val combinedItem = FilesItem(
                        name = textFile.name,
                        type = "image",
                        url = imageFile.url,
                        description = description
                    )
                    combinedData.add(combinedItem)
                    adapter.notifyItemInserted(combinedData.size - 1)
                }
            }
        }
        return combinedData
    }

    private fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun setupRecyclerView(products: List<FilesItem>) {
        adapter = ProductAdapter(products) { product ->
            val intent = Intent(this, DetailProduct::class.java).apply {
                putExtra("EXTRA_PRODUCT_NAME", product.name)
                putExtra("EXTRA_PRODUCT_TYPE", product.type)
                putExtra("EXTRA_PRODUCT_URL", product.url)
            }
            startActivity(intent)
        }

        binding.rvProducts.layoutManager = GridLayoutManager(this, 2)
        binding.rvProducts.adapter = adapter

    }
}