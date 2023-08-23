plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.pracelize)
}

val compileSdkVer : Int by rootProject.extra
val minimumSdkVer : Int by rootProject.extra

android {
  namespace = "dev.aungkyawpaing.ccdroidx.common"
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
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }

  kotlinOptions {
    jvmTarget = "1.8"
  }
}

dependencies {
  implementation(libs.timber)

  implementation(libs.coroutine.core)
  implementation(libs.coroutine.android)
  testImplementation(libs.coroutine.test)
  androidTestImplementation(libs.coroutine.test)

  implementation(libs.androidx.appcompat)
  implementation(libs.androidx.core)
}