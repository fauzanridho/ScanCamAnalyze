package com.capstone.scancamanalyze

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.scancamanalyze.data.repository.UserRepository
import com.capstone.scancamanalyze.di.Injection
import com.capstone.scancamanalyze.ui.camera.CameraViewModel
import com.capstone.scancamanalyze.ui.home.HomeViewModel
import com.capstone.scancamanalyze.ui.home.malamhari.MalamHariViewModel
import com.capstone.scancamanalyze.ui.home.pagihari.PagiHariViewModel
import com.capstone.scancamanalyze.ui.home.product.ProductViewModel
import com.capstone.scancamanalyze.ui.profile.DataStoreManager
import com.capstone.scancamanalyze.ui.profile.ProfileViewModel

class ViewModelFactory(private val repository: UserRepository, private val dataStoreManager: DataStoreManager) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(repository, dataStoreManager) as T
            }
            modelClass.isAssignableFrom(CameraViewModel::class.java) -> {
                CameraViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProductViewModel::class.java) -> {
                ProductViewModel(repository) as T
            }

            modelClass.isAssignableFrom(PagiHariViewModel::class.java) -> {
                PagiHariViewModel(repository) as T
            }

            modelClass.isAssignableFrom(MalamHariViewModel::class.java) -> {
                MalamHariViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    val repository = Injection.provideRepository(context)
                    val dataStoreManager = DataStoreManager(context)
                    INSTANCE = ViewModelFactory(repository, dataStoreManager)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}