package com.capstone.scancamanalyze.di

import android.content.Context
import com.capstone.scancamanalyze.data.pref.UserPreference
import com.capstone.scancamanalyze.data.UserRepository
import com.capstone.scancamanalyze.data.pref.dataStore

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}