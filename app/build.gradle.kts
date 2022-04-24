plugins {
  id("com.android.application")
  kotlin("android")
  kotlin("kapt")
  id("kotlin-parcelize")
  id("com.squareup.sqldelight")
  id("androidx.navigation.safeargs.kotlin")
  id("dagger.hilt.android.plugin")
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

kapt {
  correctErrorTypes = true
}

hilt {
  enableAggregatingTask = true
}

sqldelight {
  database("CCDroidXDb") {
    packageName = "dev.aungkyawpaing.ccdroidx"
    dialect = "sqlite:3.24"
    sourceFolders = listOf("sqldelight")
    schemaOutputDirectory = file("build/dbs")
  }
}

dependencies {
  coreLibraryDesugaring( "com.android.tools:desugar_jdk_libs:1.1.5")
  implementation("com.jakewharton.timber:timber:5.0.1")
  implementation("org.ocpsoft.prettytime:prettytime:5.0.2.Final")

  androidX()
  androidXArch()
  androidXActivity()
  androidXFragment()
  androidXNavigation()
  androidXWorkManager()

  implementation(Material.material)

  daggerHilt()

  retrofit()
  coroutine()
  sqlDelight()

  testImplementation("junit:junit:4.13.2")
  androidXTest()
  mockK()
}