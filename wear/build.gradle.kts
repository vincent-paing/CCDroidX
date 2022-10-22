plugins {
  id("com.android.application")
  kotlin("android")
  kotlin("kapt")
  id("dagger.hilt.android.plugin")
}

android {
  namespace = "dev.aungkyawpaing.ccdroidx"
  compileSdk = BuildConfig.compileSdk

  defaultConfig {
    applicationId = "dev.aungkyawpaing.ccdroidx"
    minSdk = BuildConfig.minSdk
    targetSdk = BuildConfig.targetSdk
    versionCode = BuildConfig.versionCode
    versionName = BuildConfig.versionName
    resourceConfigurations += setOf("en")
    setProperty("archivesBaseName", "ccdroidx-wear-${BuildConfig.versionName}")

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    testInstrumentationRunnerArguments["runnerBuilder"] =
      "de.mannodermaus.junit5.AndroidJUnit5Builder"

    buildFeatures {
      viewBinding = true
      compose = true
    }
  }

  buildTypes {
    debug {
      isMinifyEnabled = false
      isDebuggable = true
      versionNameSuffix = "-debug"
      applicationIdSuffix = ".debug"
    }
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

  composeOptions {
    kotlinCompilerExtensionVersion = "1.1.1"
  }

  kotlinOptions {
    jvmTarget = "1.8"
  }

  configurations.findByName("androidTestImplementation")?.run {
    exclude(group = "io.mockk", module = "mockk-agent-jvm")
  }

  configurations {
    androidTestImplementation {
      exclude(group = "com.google.firebase", module = "firebase-perf")
    }
  }
}

hilt {
  enableAggregatingTask = true
}

dependencies {
  coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")
  implementation(project(":common"))

  compose()
  composeWear()

  androidX()
  androidXWear()
  androidXArch()
  androidXActivity()
  androidXFragment()
  coroutine()
  coroutinePlayService()
  daggerHilt()

  junit5()
  androidXTest()
  mockK()
}