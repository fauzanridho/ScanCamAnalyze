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
    Category("Sabun Cuci Muka", R.drawable.sabuncucimuka),
    Category("Toner", R.drawable.toner)
)

data class SkinItem(val imageResId: Int, val title: String, val description: String)

val allProducts: List<ProductItem>
    get() = toner + serum + sunscreen + sabunCuciMuka + pelembab
val pelembab = listOf(
    ProductItem(
        id = 1,
        nama = "PEPTINOL Granactive Retinoid + Peptide Night Moisturizer Creme",
        image = "https://storage.googleapis.com/scancam/Produkimg/malam%20hari/pelembab/PEPTINOL%20Granactive%20Retinoid%20%2B%20Peptide%20Night%20Moisturizer%20Creme.jpg",
        detail = "A treatment night cream formulated with Granactive Retinoid, Argireline, & Korean Ginseng Water to maintain skin elasticity, smooth out skin texture, reduce hyperpigmentation, fine lines, & wrinkles. It has BOTOX LIKE EFFECT which makes skin look younger & fresher.",
        kategori = "malam"
    ),
    ProductItem(
        id = 2,
        nama = "ACNEDOT Treatment Moisturizer Gel",
        image = "https://storage.googleapis.com/scancam/Produkimg/malam%20hari/pelembab/ACNEDOT%20Treatment%20Moisturizer%20Gel.jpg",
        detail = "Pelembab wajah vegan dengan 5X Acne Combat Power yang membantu melawan bakteri penyebab jerawat, memperkecil tampilan pori, mengontrol sebum minyak berlebih, & mengurangi jerawat dalam 28 hari*. Dilengkapi dengan Postbiotic untuk mendukung proses regenerasi kulit & menyeimbangkan mikrobioma di kulit. Dengan tekstur gel ringan, tidak lengket, & tidak menjadikan kulit terlihat semakin berminyak.",
        kategori = "pagi & malam"
    )
)

val sunscreen = listOf(
    ProductItem(
        id = 1,
        nama = "Holyshield Somethinc",
        image = "https://storage.googleapis.com/scancam/Produkimg/pagi%20hari/sunscreen/Holyshield_Somethinc_6.png",
        detail = "Sunscreen Shake Mist bebas alkohol dengan SPF 46 PA+++. Memanfaatkan teknologi Encapsulated UV Filter yang memberikan perlindungan lebih maksimal dari sinar UVA & UVB. Mudah diaplikasikan kembali tanpa menggeser makeup, No White Cast, tidak lengket, & memberikan tampilan Healthy Glow Finish!",
        kategori = "pagi"
    ),
    ProductItem(
        id = 2,
        nama = "PS Holyshield! Glow Up Sunscreen Stick",
        image = "https://storage.googleapis.com/scancam/Produkimg/pagi%20hari/sunscreen/PS%20Holyshield%20Glow%20Up%20Sunscreen%20Stick.png",
        detail = "Transparent sunscreen stick dengan SPF50+ PA++++ yang mampu menangkal radiasi sinar UVA & UVB. Teksturnya ringan sehingga mudah menyerap, tidak menyumbat pori-pori, & tidak menggeser makeup. Sempurnakan perlindungan & tampilan dengan healthy glow finish yang tidak lengket ataupun berminyak. Mudah diaplikasikan kapanpun & dimanapun tanpa kontak dengan jari tangan.",
        kategori = "pagi"
    )
)

val serum = listOf(
    ProductItem(
        id = 1,
        nama = "5% Niacinamide + Moisture Sabi Beet Serum",
        image = "https://storage.googleapis.com/scancam/Produkimg/malam%20hari/serum/5%25%20Niacinamide%20%2B%20Moisture%20Sabi%20Beet%20Serum.jpg",
        detail = "Membantu mencerahkan secara maksimal, mengurangi pigmentasi, memperbaiki tekstur kulit, memperkuat skin barrier, menyamarkan noda hitam, melembabkan, & mengurangi kemerahan. Kandungan Niacinamide 10% memiliki efek & hasil yang lebih kuat pada kulit wajah dan cocok bagi kalian yang sudah terbiasa memakai Niacinamide. Cocok untuk kulit sensitif, berminyak dan berjerawat.",
        kategori = "pagi & malam"
    ),
    ProductItem(
        id = 2,
        nama = "5% Niacinamide Barrier Serum",
        image = "https://storage.googleapis.com/scancam/Produkimg/malam%20hari/serum/5%25%20Niacinamide%20Barrier%20Serum.jpg",
        detail = "5% Niacinamide Barrier Serum Serum Niacinamide dengan formulasi Vegan, 3 jenis Ceramide & Rosa Damascena Water dapat memperkuat skin barrier, mencerahkan, menyamarkan noda hitam, mengurangi kemerahan & melembabkan kulit. Cocok untuk semua jenis kulit & kulit bruntusan.",
        kategori = "pagi & malam"
    ),
    ProductItem(
        id = 3,
        nama = "10% Niacinamide + Moisture Sabi Beet Max Brightening Serum",
        image = "https://storage.googleapis.com/scancam/Produkimg/malam%20hari/serum/10%25%20Niacinamide%20%2B%20Moisture%20Sabi%20Beet%20Max%20Brightening%20Serum.jpg",
        detail = "Serum Niacinamide 10% dengan kandungan SABIWHITE x BEET sebagai #1 Brightening Agent. Hadir untuk membantu mencerahkan secara maksimal, mengurangi pigmentasi, memperbaiki tekstur kulit, memperkuat skin barrier, menyamarkan noda hitam, melembabkan, & mengurangi kemerahan. Kandungan Niacinamide 10% memiliki efek & hasil yang lebih kuat pada kulit wajah dan cocok bagi kalian yang sudah terbiasa memakai Niacinamide. Serum Somethinc 10% Niacinamide cocok untuk kulit sensitif, berminyak dan berjerawat.",
        kategori = "pagi & malam"
    ),
    ProductItem(
        id = 4,
        nama = "BAKUCHIOL Skinpair Oil Serum",
        image = "https://storage.googleapis.com/scancam/Produkimg/malam%20hari/serum/BAKUCHIOL%20Skinpair%20Oil%20Serum.jpg",
        detail = "ANTIOXIDANT, BARRIER & ACNE TREATMENT ALTERNATIVES\nSerum Vegan yang menjadi alternatif retinol dari bahan natural untuk merawat jerawat sehingga cocok digunakan untuk seluruh jenis kulit bahkan direkomendasikan untuk kulit berminyak. Bakuchiol mampu menstimulasi pembentukan collagen & memperbaiki tekstur kulit. Dikombinasikan dengan Salicylic Acid, serum ini mampu bekerja untuk menghilangkan beruntusan pada wajah, sehingga kulit menjadi halus, glowing, dan tampak kenyal. Cocok untuk pemula skincare, kulit sensitif, dan berjerawat.",
        kategori = "pagi & malam"
    ),
    ProductItem(
        id = 5,
        nama = "HYALuronic9+ Advanced + B5 Serum",
        image = "https://storage.googleapis.com/scancam/Produkimg/malam%20hari/serum/HYALuronic9%2B%20Advanced%20%2B%20B5%20Serum.jpg",
        detail = "Mengandung 9 jenis Hyaluronic Acid, B5 (Panthenol) & Chlorella, berperan penting dalam meningkatkan produksi kolagen & mengunci kelembaban maksimal hingga 48 jam ke lapisan kulit terdalam! Menenangkan kulit & membersihkan pori-pori kulit. Baik untuk semua jenis kulit & kulit sensitif.",
        kategori = "pagi & malam"
    )
)

val sabunCuciMuka = listOf(
    ProductItem(
        id = 1,
        nama = "ms glow facial wash",
        image = "https://storage.googleapis.com/scancam/Produkimg/pagi%20hari/sabun%20cuci%20muka/ms%20glow%20facial%20wash.png",
        detail = "Mencerahkan Kulit, Membersihkan debu & kotoran",
        kategori = "pagi & malam"
    ),
    ProductItem(
        id = 2,
        nama = "Acne Facial Wash",
        image = "https://storage.googleapis.com/scancam/Produkimg/pagi%20hari/sabun%20cuci%20muka/6e28ebc0-fc50-4233-b723-f349274c40fb_Acne-Facial-Wash-1.png",
        detail = "• Mugwort Hydrosol yang efektif melawan bakteri penyebab jerawat sekaligus menenangkan, dengan Triple Action Mugwort Hydrosol + Tea Tree Water + Salicylic Acid\n• Mengandung Vit C: bantu mengurangi minyak berlebih",
        kategori = "pagi & malam"
    ),
    ProductItem(
        id = 3,
        nama = "Brightly Facial Wash",
        image = "https://storage.googleapis.com/scancam/Produkimg/pagi%20hari/sabun%20cuci%20muka/094b114e-0234-47ee-8dac-a57d1fb74eac_Brightly-Facial-Wash-1.png",
        detail = "Sabun cuci muka yang membantu menjaga kelembapan kulit, mencerahkan kulit wajah, merawat kekencangan kulit, serta membantu mencerahkan kulit wajah.",
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
