package com.capstone.scancamanalyze.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.scancamanalyze.data.api.ListEventsItem
import com.capstone.scancamanalyze.databinding.ItemAnalyzeResultBinding

class AnalyzeAdapter(var article: List<ListEventsItem?>, private val onItemClick: (int: Int) -> Unit) : RecyclerView.Adapter<AnalyzeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnalyzeAdapter.ViewHolder {
        val binding = ItemAnalyzeResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnalyzeAdapter.ViewHolder, position: Int) {
        val article = article[position]
        if (article != null) {
            holder.bind(article)
        }
    }

    override fun getItemCount(): Int = article.size
    inner class ViewHolder(private val binding: ItemAnalyzeResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: ListEventsItem) {
            binding.tvLevel.text = article.name
            binding.tvDescription.text = article.summary
            Glide.with(binding.root.context)
                .load(article.imageLogo)
                .into(binding.imageResult)
            binding.root.setOnClickListener {
                article.id?.let { id ->
                    onItemClick(id)
                }
            }
        }
    }
}