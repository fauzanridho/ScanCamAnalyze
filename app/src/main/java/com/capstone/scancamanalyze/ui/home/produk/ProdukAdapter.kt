//package com.capstone.scancamanalyze.ui.home.produk
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//
////class ProdukAdapter(private val products: List<Produk>) :
////    RecyclerView.Adapter<ProdukAdapter.ProductViewHolder>() {
////
////    class ProductViewHolder(private val binding: ItemProdukBinding) :
////        RecyclerView.ViewHolder(binding.root) {
////        fun bind(product: Produk) {
////            binding.tvProductName.text = product.name
////            binding.tvProductBrand.text = product.brand
////            binding.tvProductDescription.text = product.description
////            binding.tvProductPrice.text = product.price
////            binding.ivProductImage.setImageResource(product.imageResId)
////        }
////    }
////
////    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
////        val binding = ItemProdukBinding.inflate(LayoutInflater.from(parent.context), parent, false)
////        return ProductViewHolder(binding)
////    }
////
////    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
////        holder.bind(products[position])
////    }
////
////    override fun getItemCount() = products.size
////}
