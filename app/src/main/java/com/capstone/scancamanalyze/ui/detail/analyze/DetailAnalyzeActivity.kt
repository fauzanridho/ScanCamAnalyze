package com.capstone.scancamanalyze.ui.detail.analyze

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.capstone.scancamanalyze.databinding.ActivityDetailAnalyzeBinding

class DetailAnalyzeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailAnalyzeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAnalyzeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imagePath = intent.getStringExtra("IMAGE_PATH")
        val level = intent.getStringExtra("LEVEL")
        val predictionResult = intent.getStringExtra("PREDICTION_RESULT")

        Glide.with(this)
            .load(imagePath)
            .into(binding.ivDetailPhotoAnalyze)

        binding.tvDetailAnalyze.text = "Level: $level"
        binding.tvDetailDescriptionAnalyze.text = "Prediction: $predictionResult"

    }
}