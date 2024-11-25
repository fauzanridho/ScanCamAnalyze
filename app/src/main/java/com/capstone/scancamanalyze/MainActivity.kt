package com.capstone.scancamanalyze

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.capstone.scancamanalyze.databinding.ActivityMainBinding
import com.capstone.scancamanalyze.ui.profile.DataStoreManager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inisialisasi DataStoreManager
        dataStoreManager = DataStoreManager(this)

        // Terapkan tema Dark Mode
        applyTheme()

        // Inisialisasi layout binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup Bottom Navigation
        setupBottomNavigation()

        // Hapus Action Bar
        getSupportActionBar()?.hide()
    }

    /**
     * Mengatur tema Dark Mode sesuai preferensi yang disimpan
     */
    private fun applyTheme() {
        lifecycleScope.launch {
            dataStoreManager.themeFlow.collect { isDarkMode ->
                val mode = if (isDarkMode) {
                    AppCompatDelegate.MODE_NIGHT_YES
                } else {
                    AppCompatDelegate.MODE_NIGHT_NO
                }
                AppCompatDelegate.setDefaultNightMode(mode)
            }
        }
    }

    /**
     * Mengatur Bottom Navigation dan NavController
     */
    private fun setupBottomNavigation() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_camera, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)
    }
}
