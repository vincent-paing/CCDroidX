plugins {
  id("com.android.application")
  kotlin("android")
  kotlin("kapt")
  id("com.squareup.sqldelight")
}

android {
  compileSdk = BuildConfig.compileSdk

  defaultConfig {
    applicationId = "dev.aungkyawpaing.ccdroidx"
    minSdk = BuildConfig.minSdk
    targetSdk = BuildConfig.targetSdk
    versionCode = BuildConfig.versionCode
    versionName = BuildConfig.versionName
    resourceConfigurations += setOf("en")

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    buildFeatures {
      viewBinding = true
    }


  }

  buildTypes {
    getByName("release") {
      isMinifyEnabled = true
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    isCoreLibraryDesugaringEnabled = true
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }

  kotlinOptions {
    jvmTarget = "1.8"
  }
}

//sqldelight {
//  database("CCDroidXDb") {
//    packageName = "dev.aungkyawpaing.ccdroidx"
//    dialect = "sqlite:3.24"
//  }
//}

dependencies {
  coreLibraryDesugaring( "com.android.tools:desugar_jdk_libs:1.1.5")
  implementation("com.jakewharton.timber:timber:5.0.1")

  androidX()
  androidXArch()
  androidXActivity()
  androidXFragment()
  androidXNavigation()

  implementation(Material.material)

  retrofit()
  coroutine()
  sqlDelight()

  testImplementation("junit:junit:4.13.2")
  androidXTest()
}