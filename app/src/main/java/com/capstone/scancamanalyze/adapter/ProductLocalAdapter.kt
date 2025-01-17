package com.capstone.scancamanalyze.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.scancamanalyze.R
import com.capstone.scancamanalyze.data.local.ProductEntity
import com.capstone.scancamanalyze.ui.detail.product.DetailProduct

class ProductLocalAdapter :
    ListAdapter<ProductEntity, ProductLocalAdapter.ProductViewHolder>(ProductDiffCallback()) {

    // ViewHolder untuk mengikat data dengan tampilan item
    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.ivProductImage)
        val productName: TextView = itemView.findViewById(R.id.tvProductName)
        val productDescription: TextView = itemView.findViewById(R.id.tvProductDescription)
        val productCategory: TextView = itemView.findViewById(R.id.tvKategori)
    }

    // Fungsi untuk membuat ViewHolder dan item view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    // Fungsi untuk mengikat data produk ke dalam item view
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)

        // Mengikat data produk ke tampilan
        holder.productName.text = product.productName
        holder.productDescription.text = product.description
        holder.productCategory.text = product.category

        // Menggunakan Glide untuk menampilkan gambar produk dari URL atau sumber lainnya
        Glide.with(holder.itemView.context)
            .load(product.imageUrl)  // Gantilah dengan URL gambar atau resource yang sesuai
            .into(holder.productImage)

        // Menambahkan listener klik untuk mengirim data ke activity detail
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailProduct::class.java)

            // Mengirimkan data produk ke Activity detail
            intent.putExtra("EXTRA_PRODUCT_NAME", product.productName)
            intent.putExtra("EXTRA_PRODUCT_DESCRIPTION", product.description)
            intent.putExtra("EXTRA_PRODUCT_IMAGE", product.imageUrl)
            intent.putExtra("EXTRA_PRODUCT_KATEGORI", product.category)
            intent.putExtra("EXTRA_PRODUCT_PRICE", product.price)

            context.startActivity(intent)
        }
    }


    // Menggunakan DiffUtil untuk membandingkan data lama dan baru
    class ProductDiffCallback : DiffUtil.ItemCallback<ProductEntity>() {
        override fun areItemsTheSame(oldItem: ProductEntity, newItem: ProductEntity): Boolean {
            return oldItem.id == newItem.id // Memastikan bahwa produk yang sama berdasarkan ID
        }

        override fun areContentsTheSame(oldItem: ProductEntity, newItem: ProductEntity): Boolean {
            return oldItem == newItem // Memastikan konten produk sama
        }
    }
}
