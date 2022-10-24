plugins {
  id("com.android.application")
  kotlin("android")
  kotlin("kapt")
  id("dagger.hilt.android.plugin")
}

val ENV = System.getenv()

android {
  namespace = "dev.aungkyawpaing.ccdroidx"
  compileSdk = BuildConfig.compileSdk

  defaultConfig {
    applicationId = "dev.aungkyawpaing.ccdroidx"
    minSdk = BuildConfig.minSdk
    targetSdk = BuildConfig.targetSdk
    versionCode = BuildConfig.wearVersionCode
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
  signingConfigs {
    create("release") {
      storeFile = File(rootDir, ENV["CCDROIDX_RELEASE_KEYSTORE_PATH"]!!)
      storePassword = ENV["CCDROIDX_RELEASE_KEYSTORE_PASSWORD"]!!
      keyAlias = ENV["CCDROIDX_RELEASE_KEY_ALIAS"]!!
      keyPassword = ENV["CCDROIDX_RELEASE_KEY_ALIAS_PASSWORD"]!!
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
      isMinifyEnabled = true
      isDebuggable = false
      signingConfig = signingConfigs.getByName("release")
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
  timber()
  implementation(project(":common"))
  implementation(project(":weardatalayer"))

  implementation(AndroidXDataStore.preference)

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