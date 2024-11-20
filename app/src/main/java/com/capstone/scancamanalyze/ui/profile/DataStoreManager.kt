package com.capstone.scancamanalyze.ui.profile

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.capstone.scancamanalyze.data.pref.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(context: Context) {
    private val dataStore = context.dataStore

    companion object {
        val THEME_KEY = booleanPreferencesKey("dark_mode")
    }

    suspend fun setTheme(isDarkMode: Boolean) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkMode
        }
    }

    val themeFlow: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[THEME_KEY] ?: false
        }
}