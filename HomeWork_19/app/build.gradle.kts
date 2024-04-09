plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)

    //To use Kotlin Symbol Processing (KSP) and see build.gradle.kts(Module)
    id("com.google.devtools.ksp")

    // Parcelize
    id("org.jetbrains.kotlin.plugin.parcelize")

    // Hilt
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.attractions"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.attractions"
        minSdk = 24
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
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Coroutines
    implementation(libs.kotlinx.coroutines.core)

    //Fragments
    implementation(libs.androidx.fragment.ktx)

    // MVVM
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    // Navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Glide
    implementation(libs.glide)

    //Retrofit
    implementation(libs.retrofit)

    // Moshi
    implementation(libs.moshi)
    ksp(libs.moshi.kotlin.codegen)
    implementation(libs.converter.moshi)
    implementation(libs.moshi.kotlin)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    //Location
    implementation(libs.play.services.location)

    // Yandex Maps
    implementation (libs.maps.mobile)
}