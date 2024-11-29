package com.capstone.scancamanalyze.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AnalyzeDao {
    @Insert
    suspend fun insertAnalyze(analyze: AnalyzeEntity)

    @Query("SELECT * FROM analyze_history ORDER BY timestamp DESC")
    suspend fun getAllAnalyzes(): List<AnalyzeEntity>
}