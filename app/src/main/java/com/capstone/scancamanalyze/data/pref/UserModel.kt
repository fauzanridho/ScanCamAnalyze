package com.capstone.scancamanalyze.data.pref

data class UserModel(
    val name: String,
    val email: String,
    val isLogin: Boolean = false
)