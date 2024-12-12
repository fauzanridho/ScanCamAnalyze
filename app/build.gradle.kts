plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp")
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.capstone.scancamanalyze"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.capstone.scancamanalyze"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        mlModelBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.datastore.core.android)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.espresso.core)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.constraintlayout)

    implementation(libs.converter.gson)
    implementation(libs.retrofit)
    implementation(libs.logging.interceptor)
    implementation(libs.viewmodel.ktx)
    implementation(libs.livedata.ktx)

    implementation(libs.glide)

    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.lifecycle.viewmodel.ktx.v261)
    implementation(libs.androidx.lifecycle.livedata.ktx.v261)
    implementation(libs.androidx.activity.ktx)

    implementation(libs.tensorflow.lite.task.vision)
    implementation(libs.tensorflow.lite.support.v044)
    implementation(libs.tensorflow.lite.metadata.v044)
    implementation(libs.tensorflow.lite.task.vision)

    ksp(libs.room.compiler)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.lottie)
    implementation(libs.okhttp)

    implementation(libs.hilt.android)



}