package com.capstone.scancamanalyze.di

import android.content.Context
import com.capstone.scancamanalyze.data.local.AnalyzeDatabase
import com.capstone.scancamanalyze.data.local.ProductDatabase
import com.capstone.scancamanalyze.data.pref.UserPreference
import com.capstone.scancamanalyze.data.pref.dataStore
import com.capstone.scancamanalyze.data.repository.UserRepository

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val database = AnalyzeDatabase.getDatabase(context)
        val dao = database.analyzeDao()
        val productDatabase = ProductDatabase.getDatabase(context)
        val productDao = productDatabase.productDao()
        return UserRepository.getInstance(pref, dao, productDao)
    }
}