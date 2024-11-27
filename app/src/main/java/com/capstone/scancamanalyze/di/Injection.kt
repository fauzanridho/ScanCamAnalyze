package com.capstone.scancamanalyze.di

import android.content.Context
import com.capstone.scancamanalyze.data.pref.UserPreference
import com.capstone.scancamanalyze.data.UserRepository
import com.capstone.scancamanalyze.data.local.AnalyzeDatabase
import com.capstone.scancamanalyze.data.pref.dataStore

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val database = AnalyzeDatabase.getDatabase(context)
        val dao = database.analyzeDao()
        return UserRepository.getInstance(pref,dao)
    }
}