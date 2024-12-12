package com.capstone.scancamanalyze

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.capstone.scancamanalyze.data.pref.UserPreference
import com.capstone.scancamanalyze.data.pref.dataStore
import com.capstone.scancamanalyze.databinding.ActivityMainBinding
import com.capstone.scancamanalyze.ui.login.LoginActivity
import com.capstone.scancamanalyze.ui.profile.DataStoreManager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var dataStoreManager: DataStoreManager
    private lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataStoreManager = DataStoreManager(this)
        userPreference = UserPreference.getInstance(dataStore)

        applyTheme()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            userPreference.getSession().collect { user ->
                if (!user.isLogin) {
                    startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                    finish()
                } else {
                    setupBottomNavigation()
                }
            }
        }

        setupBottomNavigation()
        getSupportActionBar()?.hide()
    }

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
