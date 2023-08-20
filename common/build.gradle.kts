plugins {
  id("com.android.library")
  id("org.jetbrains.kotlin.android")
  id("kotlin-parcelize")
}

android {
  namespace = "dev.aungkyawpaing.ccdroidx.common"
  compileSdk = BuildConfig.compileSdk

  defaultConfig {
    minSdk = BuildConfig.minSdk
    targetSdk = BuildConfig.targetSdk

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    consumerProguardFiles("consumer-rules.pro")
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  kotlinOptions {
     jvmTarget = "17"
  }
}

dependencies {
  timber()
  coroutine()
  androidX()
}