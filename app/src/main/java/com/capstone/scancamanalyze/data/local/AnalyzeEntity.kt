package com.capstone.scancamanalyze.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "analyze_history")
data class AnalyzeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val imageName: String,
    val level: Int,
    val predictionResult: String,
    val timestamp: Long = System.currentTimeMillis()
)
