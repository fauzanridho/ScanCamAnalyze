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

            binding.tvProductName.text = product.name
            binding.tvProductDescription.text = product.description


            if (product.type == "image") {
                Glide.with(binding.root.context)
                    .load(product.url)
                    .into(binding.ivProductImage)
            } else {
                binding.ivProductImage.setImageResource(android.R.color.transparent)
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
            val intent = Intent(holder.itemView.context, DetailProduct::class.java)
            intent.putExtra("EXTRA_PRODUCT_NAME", product.name)
            intent.putExtra("EXTRA_PRODUCT_TYPE", product.type)
            intent.putExtra(
                "EXTRA_PRODUCT_IMAGE",
                product.url
            )
            intent.putExtra(
                "EXTRA_PRODUCT_DESCRIPTION",
                product.description
            )
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = products.size

    fun updateData(newProducts: List<FilesItem>) {
        products = newProducts
        notifyDataSetChanged()
    }
}
