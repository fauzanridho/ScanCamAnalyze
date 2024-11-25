package com.capstone.scancamanalyze.ui.detail.analyze

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.scancamanalyze.databinding.ActivityDetailAnalyzeBinding

class DetailAnalyzeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailAnalyzeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAnalyzeBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}