package com.capstone.scancamanalyze.data

import com.capstone.scancamanalyze.R
import com.capstone.scancamanalyze.data.api.ProductItem

data class Category(
    val name: String,
    val imageResId: Int
)

data class Product(
    val name: String,
    val brand: String,
    val description: String,
    val price: String,
    val imageResId: Int,
    val kategori: String
)

val categories = listOf(

    Category("Pelembab", R.drawable.pelembabmuka),
    Category("Sunscreen", R.drawable.sunscreen),
    Category("Serum", R.drawable.serum),
    Category("Sabun Cuci Muka", R.drawable.sabuncucimuka),
    Category("Toner", R.drawable.toner)
)

data class SkinItem(val imageResId: Int, val title: String, val description: String)

val allProducts: List<ProductItem>
    get() = toner + serum + sunscreen + sabunCuciMuka + pelembab
val pelembab = listOf(
    ProductItem(
        id = 5,
        nama = "SUPPLE POWER Hyaluronic9+ Onsen Essence Toner",
        image = "https://storage.googleapis.com/scancam/Produkimg/malam%20hari/toner/Somethinc_SupplePower_Duo_PackShot_800x9801.jpg",
        detail = "Hydrating Toner yang mengandung kesegaran 79% Onsen Belgium Hot Spring Water dan 9 Hyaluronic Acid yang mampu menghidrasi kulit hingga pori-pori terdalam, menjaga elastisitas kulit, & mempersiapkan kulit untuk menyerap serum + pelembab secara maksimal.",
        kategori = "pagi & malam"
    )
)
val sunscreen = listOf(
    ProductItem(
        id = 4,
        nama = "toner glowing",
        image = "https://storage.googleapis.com/scancam/Produkimg/malam%20hari/toner/toner%20glowing.png",
        detail = "Menyeimbangkan pH kulit,Mempersiapkan kulit untuk tahapan skincare selanjutnya",
        kategori = "pagi & malam"
    )
)
val serum = listOf(
    ProductItem(
        id = 3,
        nama = "CALM DOWN TONER",
        image = "https://storage.googleapis.com/scancam/Produkimg/malam%20hari/toner/CALM_DOWN_PDP_TONER_100ML_POLOS.jpg",
        detail = "Soothing Toner yang diformulasikan dengan Madagascar Centella Asiatica & Adaptogenic Cherimoya untuk menenangkan kulit kemerahan, sensitif, & teriritasi ringan. Dilengkapi 3% PHA yang efektif mengangkat kotoran serta sel kulit mati penyebab komedo & kulit kusam. Membantu menyamarkan bekas jerawat PIE, menghaluskan, sekaligus menghidrasi kulit. Cocok untuk kulit sensitif, berjerawat, kering, & mudah mengalami kemerahan.",
        kategori = "pagi & malam"
    )
)
val sabunCuciMuka = listOf(
    ProductItem(
        id = 2,
        nama = "Skin_Goals_Vita_Propolis_Glow_Essence_Toner_100ml_1",
        image = "https://storage.googleapis.com/scancam/Produkimg/pagi%20hari/toner/Skin_Goals_Vita_Propolis_Glow_Essence_Toner_100ml_1.png",
        detail = "Toner pH Balance dengan kandungan Vit C (EAA), Korean Propolis, New Zealand Manuka Honey, untuk menutrisi & mencerahkan kulit wajah dalam 28 hari*. Diperkaya dengan kandungan Pro-Vitamin B5 yang melembabkan & menjadikan kulit wajah tampak lebih sehat & glowing sepanjang hari.",
        kategori = "pagi & malam"
    )
)
val toner = listOf(
    ProductItem(
        id = 1,
        nama = "Acnedot_Treatment_Toner_100ml",
        image = "https://storage.googleapis.com/scancam/Produkimg/pagi%20hari/toner/Acnedot_Treatment_Toner_100ml.png",
        detail = "Toner pH balance vegan untuk kulit berjerawat yang membantu memperkecil tampilan pori & menyeimbangkan minyak berlebih pada wajah. Dengan kandungan 5X Acne Combat Power & 91% Korean Green Tea Water untuk melawan bakteri penyebab jerawat sekaligus menenangkan kemerahan pada kulit berjerawat.",
        kategori = "pagi & malam"
    ),
    ProductItem(
        id = 2,
        nama = "Skin_Goals_Vita_Propolis_Glow_Essence_Toner_100ml_1",
        image = "https://storage.googleapis.com/scancam/Produkimg/pagi%20hari/toner/Skin_Goals_Vita_Propolis_Glow_Essence_Toner_100ml_1.png",
        detail = "Toner pH Balance dengan kandungan Vit C (EAA), Korean Propolis, New Zealand Manuka Honey, untuk menutrisi & mencerahkan kulit wajah dalam 28 hari*. Diperkaya dengan kandungan Pro-Vitamin B5 yang melembabkan & menjadikan kulit wajah tampak lebih sehat & glowing sepanjang hari.",
        kategori = "pagi & malam"
    ),
    ProductItem(
        id = 3,
        nama = "CALM DOWN TONER",
        image = "https://storage.googleapis.com/scancam/Produkimg/malam%20hari/toner/CALM_DOWN_PDP_TONER_100ML_POLOS.jpg",
        detail = "Soothing Toner yang diformulasikan dengan Madagascar Centella Asiatica & Adaptogenic Cherimoya untuk menenangkan kulit kemerahan, sensitif, & teriritasi ringan. Dilengkapi 3% PHA yang efektif mengangkat kotoran serta sel kulit mati penyebab komedo & kulit kusam. Membantu menyamarkan bekas jerawat PIE, menghaluskan, sekaligus menghidrasi kulit. Cocok untuk kulit sensitif, berjerawat, kering, & mudah mengalami kemerahan.",
        kategori = "pagi & malam"
    ),
    ProductItem(
        id = 4,
        nama = "toner glowing",
        image = "https://storage.googleapis.com/scancam/Produkimg/malam%20hari/toner/toner%20glowing.png",
        detail = "Menyeimbangkan pH kulit,Mempersiapkan kulit untuk tahapan skincare selanjutnya",
        kategori = "pagi & malam"
    ),
    ProductItem(
        id = 5,
        nama = "SUPPLE POWER Hyaluronic9+ Onsen Essence Toner",
        image = "https://storage.googleapis.com/scancam/Produkimg/malam%20hari/toner/Somethinc_SupplePower_Duo_PackShot_800x9801.jpg",
        detail = "Hydrating Toner yang mengandung kesegaran 79% Onsen Belgium Hot Spring Water dan 9 Hyaluronic Acid yang mampu menghidrasi kulit hingga pori-pori terdalam, menjaga elastisitas kulit, & mempersiapkan kulit untuk menyerap serum + pelembab secara maksimal.",
        kategori = "pagi & malam"
    )
)