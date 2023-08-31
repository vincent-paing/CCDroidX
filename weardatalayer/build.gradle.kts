plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.ksp)
  alias(libs.plugins.dagger.hilt)
}

val compileSdkVer : Int by rootProject.extra
val minimumSdkVer : Int by rootProject.extra

android {
  namespace = "dev.aungkyawpaing.wear.datalayer"
  compileSdk = compileSdkVer

  defaultConfig {
    minSdk = minimumSdkVer

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
  implementation(project(":common"))
  implementation(libs.timber)

  implementation(libs.coroutine.core)
  implementation(libs.coroutine.android)
  testImplementation(libs.coroutine.test)
  androidTestImplementation(libs.coroutine.test)
  implementation(libs.coroutine.playServices)

  implementation(libs.moshi.core)
  implementation(libs.moshi.adapters)
  implementation(libs.moshi.kotlin)
  ksp(libs.moshi.codeGen)

  implementation(libs.dagger.hilt.android)
  implementation(libs.dagger.hilt.work)
  ksp(libs.dagger.hilt.compiler)
  ksp(libs.dagger.hilt.android.compiler)
  androidTestImplementation(libs.dagger.hilt.android.testing)
  kspAndroidTest(libs.dagger.hilt.compiler)
  kspAndroidTest(libs.dagger.hilt.android.compiler)
  testImplementation(libs.dagger.hilt.android.testing)
  kspTest(libs.dagger.hilt.compiler)
  kspTest(libs.dagger.hilt.android.compiler)

  implementation(libs.androidx.wear.playservices)

  testImplementation(libs.junit.jupiter.api)
  testRuntimeOnly(libs.junit.jupiter.engine)
  testImplementation(libs.junit.jupiter.params)
  testImplementation(libs.junit.junit4)
  testRuntimeOnly(libs.junit.jupiter.vintageEngine)
  androidTestImplementation(libs.junit.jupiter.api)
  androidTestImplementation(libs.androidJunit5.core)
  androidTestRuntimeOnly(libs.androidJunit5.runner)
}