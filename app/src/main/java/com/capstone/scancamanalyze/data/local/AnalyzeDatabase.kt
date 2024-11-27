package com.capstone.scancamanalyze.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AnalyzeEntity::class], version = 1, exportSchema = false)
abstract class AnalyzeDatabase : RoomDatabase() {
    abstract fun analyzeDao(): AnalyzeDao

    companion object {
        @Volatile
        private var INSTANCE: AnalyzeDatabase? = null

        fun getDatabase(context: Context): AnalyzeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AnalyzeDatabase::class.java,
                    "analyze_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}