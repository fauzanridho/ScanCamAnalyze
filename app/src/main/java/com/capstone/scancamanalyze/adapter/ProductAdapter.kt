package com.capstone.scancamanalyze.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.scancamanalyze.data.api.ProductItem

import com.capstone.scancamanalyze.databinding.ItemProductBinding
import com.capstone.scancamanalyze.ui.detail.product.DetailProduct

class ProductAdapter(private var products: List<ProductItem>, private val onClick: (ProductItem) -> Unit) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductItem, onClick: (ProductItem) -> Unit) {
            binding.tvProductName.text = product.nama
            binding.tvProductDescription.text = product.detail
            binding.tvKategori.text = product.kategori // Placeholder harga, sesuaikan jika ada field harga

            // Gunakan Glide untuk memuat gambar dari URL
            Glide.with(binding.root.context)
                .load(product.image) // URL gambar dari field `image` di TonerItem
                .into(binding.ivProductImage)

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
            intent.putExtra("EXTRA_PRODUCT_NAME", product.nama)
            intent.putExtra("EXTRA_PRODUCT_DETAIL", product.detail)
            intent.putExtra("EXTRA_PRODUCT_IMAGE", product.image)
            intent.putExtra("EXTRA_PRODUCT_KATEGORI", product.kategori)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = products.size

    fun updateData(newProducts: List<ProductItem>) {
        products = newProducts
        notifyDataSetChanged()
    }
}
