package com.capstone.scancamanalyze.adapter

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.scancamanalyze.data.local.AnalyzeEntity
import com.capstone.scancamanalyze.databinding.ItemAnalyzeResultBinding
import com.capstone.scancamanalyze.ui.detail.analyze.DetailAnalyzeActivity

class AnalyzeAdapter(
    var analyzeList: List<AnalyzeEntity>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<AnalyzeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAnalyzeResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val analyze = analyzeList[position]
        holder.bind(analyze)
    }

    override fun getItemCount(): Int = analyzeList.size

    inner class ViewHolder(private val binding: ItemAnalyzeResultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(analyze: AnalyzeEntity) {
            binding.tvLevel.text = "Level: ${analyze.level}"
            binding.tvDescription.text = "Prediction: ${analyze.predictionResult}"

            Glide.with(binding.root.context)
                .load(Uri.parse(analyze.imageName))  // Pastikan imageName adalah URI yang valid
                .into(binding.imageResult)

            binding.root.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, DetailAnalyzeActivity::class.java).apply {
                    putExtra("analyze_ID", analyze.id)
                    putExtra("IMAGE_PATH", analyze.imageName)
                    putExtra("LEVEL", analyze.level)
                    putExtra("PREDICTION_RESULT", analyze.predictionResult)
                }
                context.startActivity(intent)
                Log.d("AnalyzeAdapter", "Image URI: ${analyze.imageName}")
            }
        }
    }
}