package com.capstone.scancamanalyze.ui.log

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.scancamanalyze.R
import com.capstone.scancamanalyze.adapter.SkinItem

class LogViewModel : ViewModel() {

    private val _logs = MutableLiveData<List<SkinItem>>().apply {
        value = listOf(
            SkinItem(R.drawable.avatar, "Level 1", "Deskripsi hasil analisis pertama"),
            SkinItem(R.drawable.avatar, "Level 2", "Deskripsi hasil analisis kedua"),
            SkinItem(R.drawable.avatar, "Level 3", "Deskripsi hasil analisis ketiga")
        )
    }
    val logs: LiveData<List<SkinItem>> = _logs
}
