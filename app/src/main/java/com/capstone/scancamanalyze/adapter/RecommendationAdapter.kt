package com.capstone.scancamanalyze.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.scancamanalyze.data.Product
import com.capstone.scancamanalyze.databinding.ItemProductBinding

class RecommendationAdapter(
    private var productList: List<Product>
) : RecyclerView.Adapter<RecommendationAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.tvProductName.text = product.name
            binding.tvProductDescription.text = product.description
            binding.tvKategori.text = product.price

            Glide.with(binding.root.context)
                .load(product.imageResId)
                .into(binding.ivProductImage)

            binding.root.setOnClickListener{}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
    }

    override fun getItemCount() = productList.size

    fun updateData(newProducts: List<Product>) {
        productList = newProducts
        notifyDataSetChanged()
    }
}
