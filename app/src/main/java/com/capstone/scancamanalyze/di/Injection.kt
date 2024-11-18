package com.capstone.scancamanalyze.di

import android.content.Context
import com.capstone.scancamanalyze.pref.UserPreference
import com.capstone.scancamanalyze.pref.UserRepository
import com.capstone.scancamanalyze.pref.dataStore

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}