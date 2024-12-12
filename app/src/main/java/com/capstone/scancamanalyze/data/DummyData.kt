package com.capstone.scancamanalyze.data

import com.capstone.scancamanalyze.R
import com.capstone.scancamanalyze.data.api.AvatarItem
import com.capstone.scancamanalyze.data.api.ProductItem

data class Category(
    val name: String,
    val imageResId: Int
)

val categories = listOf(

    Category("Pelembab", R.drawable.pelembabmuka),
    Category("Sunscreen", R.drawable.sunscreen),
    Category("Serum", R.drawable.serum),
    Category("Facialwash", R.drawable.sabuncucimuka),
    Category("Toner", R.drawable.toner)
)
val avatars = listOf(
    AvatarItem(id = 1, image = "https://storage.googleapis.com/scancam/Avatar/avataaars9.png"),
    AvatarItem(id = 2, image = "https://storage.googleapis.com/scancam/Avatar/avataaars8.png"),
    AvatarItem(id = 3, image = "https://storage.googleapis.com/scancam/Avatar/avataaars7.png"),
    AvatarItem(id = 4, image = "https://storage.googleapis.com/scancam/Avatar/avataaars6.png"),
    AvatarItem(id = 5, image = "https://storage.googleapis.com/scancam/Avatar/avataaars5.png"),
    AvatarItem(id = 6, image = "https://storage.googleapis.com/scancam/Avatar/avataaars4.png"),
    AvatarItem(id = 7, image = "https://storage.googleapis.com/scancam/Avatar/avataaars3.png"),
    AvatarItem(id = 8, image = "https://storage.googleapis.com/scancam/Avatar/avataaars24.png"),
    AvatarItem(id = 9, image = "https://storage.googleapis.com/scancam/Avatar/avataaars23.png"),
    AvatarItem(id = 10, image = "https://storage.googleapis.com/scancam/Avatar/avataaars22.png"),
    AvatarItem(id = 11, image = "https://storage.googleapis.com/scancam/Avatar/avataaars21.png"),
    AvatarItem(id = 12, image = "https://storage.googleapis.com/scancam/Avatar/avataaars20.png"),
    AvatarItem(id = 13, image = "https://storage.googleapis.com/scancam/Avatar/avataaars2.png"),
    AvatarItem(id = 14, image = "https://storage.googleapis.com/scancam/Avatar/avataaars19.png"),
    AvatarItem(id = 15, image = "https://storage.googleapis.com/scancam/Avatar/avataaars18.png"),
    AvatarItem(id = 16, image = "https://storage.googleapis.com/scancam/Avatar/avataaars17.png"),
    AvatarItem(id = 17, image = "https://storage.googleapis.com/scancam/Avatar/avataaars16.png"),
    AvatarItem(id = 18, image = "https://storage.googleapis.com/scancam/Avatar/avataaars15.png"),
    AvatarItem(id = 19, image = "https://storage.googleapis.com/scancam/Avatar/avataaars14.png"),
    AvatarItem(id = 20, image = "https://storage.googleapis.com/scancam/Avatar/avataaars13.png"),
    AvatarItem(id = 21, image = "https://storage.googleapis.com/scancam/Avatar/avataaars12.png"),
    AvatarItem(id = 22, image = "https://storage.googleapis.com/scancam/Avatar/avataaars11.png"),
    AvatarItem(id = 23, image = "https://storage.googleapis.com/scancam/Avatar/avataaars10.png"),
    AvatarItem(id = 24, image = "https://storage.googleapis.com/scancam/Avatar/avataaars1.png"),
    AvatarItem(id = 25, image = "https://storage.googleapis.com/scancam/Avatar/avataaars.png")
)
