plugins {
  id("com.android.library")
  kotlin("android")
  kotlin("kapt")
  id("kotlin-parcelize")
  id("dagger.hilt.android.plugin")
  id("com.squareup.sqldelight")
  id("de.mannodermaus.android-junit5")
}

android {
  namespace = "dev.aungkyawpaing.ccdroidx.data"
  compileSdk = BuildConfig.compileSdk


  defaultConfig {
    minSdk = BuildConfig.minSdk
    targetSdk = BuildConfig.targetSdk

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    testInstrumentationRunnerArguments["runnerBuilder"] =
      "de.mannodermaus.junit5.AndroidJUnit5Builder"

    consumerProguardFiles("consumer-rules.pro")
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }

  compileOptions {
    isCoreLibraryDesugaringEnabled = true
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }

  testOptions {
    unitTests {
      isIncludeAndroidResources = true
    }
  }

  kotlinOptions {
    jvmTarget = "1.8"
  }

  configurations.findByName("androidTestImplementation")?.run {
    exclude(group = "io.mockk", module = "mockk-agent-jvm")
  }
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
  coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")
  implementation(project(":common"))

  coroutine()

  timber()

  androidX()

  retrofit()
  sqlDelight()

  daggerHilt()

  junit5()
  androidXTest()
  mockK()
}