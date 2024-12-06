package com.capstone.scancamanalyze.ui.home.product

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.scancamanalyze.databinding.ItemProductBinding
import com.capstone.scancamanalyze.ui.home.product.detailproduct.DetailProduct

class ProductAdapter(private val products: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product, onClick: (Product) -> Unit) {
            binding.tvProductName.text = product.name
            binding.tvProductBrand.text = product.brand
            binding.tvProductDescription.text = product.description
            binding.tvProductPrice.text = product.price
            binding.ivProductImage.setImageResource(product.imageResId)

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
            intent.putExtra("EXTRA_PRODUCT_BRAND", product.brand)
            intent.putExtra("EXTRA_PRODUCT_DESCRIPTION", product.description)
            intent.putExtra("EXTRA_PRODUCT_PRICE", product.price)
            intent.putExtra("EXTRA_PRODUCT_IMAGE", product.imageResId)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = products.size
}