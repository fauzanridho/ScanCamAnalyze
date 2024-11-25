package com.capstone.scancamanalyze.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.scancamanalyze.R

data class SkinItem(val imageResId: Int, val title: String, val description: String)

class SkinAdapter(
    private val items: List<SkinItem>,
    private val onItemClick: ((SkinItem) -> Unit)? = null
) : RecyclerView.Adapter<SkinAdapter.SkinViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkinViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_analyze_result, parent, false)
        return SkinViewHolder(view)
    }

    override fun onBindViewHolder(holder: SkinViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, onItemClick)
    }

    override fun getItemCount(): Int = items.size

    class SkinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.image_result)
        private val titleView: TextView = itemView.findViewById(R.id.tv_level)
        private val descriptionView: TextView = itemView.findViewById(R.id.tv_description)

        fun bind(item: SkinItem, onItemClick: ((SkinItem) -> Unit)?) {
            imageView.setImageResource(item.imageResId)
            titleView.text = item.title
            descriptionView.text = item.description

            itemView.setOnClickListener {
                onItemClick?.invoke(item)
            }
        }
    }
}
