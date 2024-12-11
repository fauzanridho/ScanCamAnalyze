package com.capstone.scancamanalyze.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.scancamanalyze.data.api.FilesItem
import com.capstone.scancamanalyze.databinding.ItemProductBinding
import com.capstone.scancamanalyze.ui.detail.product.DetailProduct

class ProductAdapter(
    private var products: List<FilesItem>,
    private val onClick: (FilesItem) -> Unit
) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: FilesItem, onClick: (FilesItem) -> Unit) {
            // Set product name

            binding.tvProductName.text = product.name
            binding.tvProductDescription.text = product.description


            // Use Glide to load image for the products with type "image"
            if (product.type == "image") {
                Glide.with(binding.root.context)
                    .load(product.url)
                    .into(binding.ivProductImage)
            } else {
                // Handle text or other types (you may need a different UI for them)
                binding.ivProductImage.setImageResource(android.R.color.transparent) // Hide image for non-image types
            }

            binding.root.setOnClickListener {
                onClick(product)
            }
        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product) {
            // On click, pass the data to DetailProduct Activity
            val intent = Intent(holder.itemView.context, DetailProduct::class.java)
            intent.putExtra("EXTRA_PRODUCT_NAME", product.name)
            intent.putExtra("EXTRA_PRODUCT_TYPE", product.type) // Passing the type if needed
            intent.putExtra(
                "EXTRA_PRODUCT_IMAGE",
                product.url
            ) // Passing the URL as image or description
            intent.putExtra(
                "EXTRA_PRODUCT_DESCRIPTION",
                product.description
            ) // Passing the description
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = products.size

    // Update the data in the adapter
    fun updateData(newProducts: List<FilesItem>) {
        products = newProducts
        notifyDataSetChanged()
    }
}
