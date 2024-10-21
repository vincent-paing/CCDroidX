plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.ksp)
  alias(libs.plugins.kotlin.compose.compiler)
  alias(libs.plugins.dagger.hilt)
}

val ENV = System.getenv()

val compileSdkVer : Int by rootProject.extra
val targetSdkVer : Int by rootProject.extra
val minimumSdkVer : Int by rootProject.extra
val versionNameConfig : String by rootProject.extra
val wearVersionCodeConfig : Int by rootProject.extra

android {
  namespace = "dev.aungkyawpaing.ccdroidx"
  compileSdk = compileSdkVer

  defaultConfig {
    applicationId = "dev.aungkyawpaing.ccdroidx"
    minSdk = minimumSdkVer
    targetSdk = targetSdkVer
    versionCode = wearVersionCodeConfig
    versionName = versionNameConfig
    resourceConfigurations += setOf("en")
    setProperty("archivesBaseName", "ccdroidx-wear-${versionNameConfig}")

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
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  testOptions {
    unitTests {
      isIncludeAndroidResources = true
    }
  }

  kotlinOptions {
    jvmTarget = "17"
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
  coreLibraryDesugaring(libs.desugar.jdk.libs)
  implementation(project(":common"))
  implementation(project(":weardatalayer"))

  implementation(libs.timber)
  implementation(libs.androidx.dataStore.preference)

  implementation(platform(libs.compose.bom))
  implementation(libs.compose.ui)
  implementation(libs.compose.ui.tooling)
  implementation(libs.compose.ui.tooling.preview)
  testImplementation(libs.compose.ui.test)
  androidTestImplementation(platform(libs.compose.bom))
  androidTestImplementation(libs.compose.ui.test)
  debugImplementation(libs.compose.ui.test.manifest)
  implementation(libs.compose.foundation)
  implementation(libs.compose.animation)
  implementation(libs.compose.material.icon.extended)
  implementation(libs.compose.constraintLayout)
  implementation(libs.compose.liveData)
  implementation(libs.compose.viewModel)

  implementation(libs.compose.wear.material)
  implementation(libs.compose.wear.foundation)
  implementation(libs.compose.wear.navigation)

  implementation(libs.androidx.appcompat)
  implementation(libs.androidx.core)

  implementation(libs.androidx.wear)
  implementation(libs.androidx.wear.complication.dataSource)
  implementation(libs.androidx.wear.remoteInteractions)
  implementation(libs.androidx.wear.playservices)

  implementation(libs.androidx.lifecycle.viewModel)
  implementation(libs.androidx.lifecycle.liveData)
  implementation(libs.androidx.lifecycle.extensions)
  implementation(libs.androidx.lifecycle.java8)
  implementation(libs.androidx.lifecycle.service)
  testImplementation(libs.androidx.arch.testing)

  implementation(libs.androidx.activity)
  implementation(libs.androidx.fragment)
  testImplementation(libs.androidx.fragment.testing)
  androidTestImplementation(libs.androidx.fragment.testing)

  implementation(libs.coroutine.core)
  implementation(libs.coroutine.android)
  testImplementation(libs.coroutine.test)
  androidTestImplementation(libs.coroutine.test)
  implementation(libs.coroutine.playServices)

  implementation(libs.dagger.hilt.android)
  ksp(libs.dagger.hilt.compiler)
  ksp(libs.dagger.hilt.android.compiler)
  androidTestImplementation(libs.dagger.hilt.android.testing)
  kspAndroidTest(libs.dagger.hilt.compiler)
  kspAndroidTest(libs.dagger.hilt.android.compiler)
  testImplementation(libs.dagger.hilt.android.testing)
  kspTest(libs.dagger.hilt.compiler)
  kspTest(libs.dagger.hilt.android.compiler)

  testImplementation(platform(libs.junit.junit5.bom))
  testImplementation(libs.junit.jupiter.api)
  testRuntimeOnly(libs.junit.jupiter.engine)
  testImplementation(libs.junit.jupiter.params)
  testImplementation(libs.junit.junit4)
  testRuntimeOnly(libs.junit.jupiter.vintageEngine)
  androidTestImplementation(platform(libs.junit.junit5.bom))
  androidTestImplementation(libs.junit.jupiter.api)

  testImplementation(libs.androidx.test.core)
  testImplementation(libs.androidx.test.runner)
  testImplementation(libs.androidx.test.rules)
  testImplementation(libs.androidx.test.ext.junit)
  testImplementation(libs.androidx.test.ext.truth)
  androidTestImplementation(libs.androidx.test.core)
  androidTestImplementation(libs.androidx.test.runner)
  androidTestImplementation(libs.androidx.test.rules)
  androidTestImplementation(libs.androidx.test.ext.junit)
  androidTestImplementation(libs.androidx.test.ext.truth)

  testImplementation(libs.mockk)
  testImplementation(libs.mockk.agentJvm)
  androidTestImplementation(libs.mockk.agentJvm)
  androidTestImplementation(libs.mockk.android)
}