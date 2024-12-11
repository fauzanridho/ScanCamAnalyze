package com.capstone.scancamanalyze.ui.home.pagihari

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
import com.capstone.scancamanalyze.databinding.ActivityPagiHariBinding
import com.capstone.scancamanalyze.ui.detail.product.DetailProduct
import java.util.Locale


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

        val productName =
            intent.getStringExtra("CATEGORY_NAME")?.lowercase(Locale.ROOT) ?: "toner"

        // Fetch sunscreen data using ViewModel
        viewModel.fetchProduct()

        // Observe the LiveData from the ViewModel
        observeViewModel()

    }

    private fun observeViewModel() {

        // Observe the sunscreen data
        viewModel.productData.observe(this, Observer { products ->
            products?.let {
                val product = it.files?.filterNotNull() ?: emptyList()

                // Menggabungkan data gambar dan teks sesuai urutan
                val combinedData = combineData(product)
                setupRecyclerView(combinedData)
            }
        })

        // Observe error message
        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            errorMessage?.let {
                // Handle the error (e.g., show a Toast or Snackbar)
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
                // Fetch description using viewModel.fetchText
                viewModel.fetchText(textFile.url!!) { description ->
                    val combinedItem = FilesItem(
                        name = textFile.name,
                        type = "image",
                        url = imageFile.url,
                        description = description // Set the fetched description
                    )
                    combinedData.add(combinedItem)
                    // Update the adapter after fetching the description
                    adapter.notifyItemInserted(combinedData.size - 1)
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
        adapter = ProductAdapter(products) { product ->
            val intent = Intent(this, DetailProduct::class.java).apply {
                putExtra("EXTRA_PRODUCT_NAME", product.name)
                putExtra("EXTRA_PRODUCT_TYPE", product.type)  // Update based on the data fields
                putExtra("EXTRA_PRODUCT_URL", product.url)   // Pass the URL for the image
            }
            startActivity(intent)
        }

        binding.rvProducts.layoutManager = GridLayoutManager(this, 2)
        binding.rvProducts.adapter = adapter

    }
}