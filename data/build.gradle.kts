plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.kapt)
  alias(libs.plugins.dagger.hilt)
  alias(libs.plugins.sqldelight)
  alias(libs.plugins.android.junit5)
}

val compileSdkVer : Int by rootProject.extra
val minimumSdkVer : Int by rootProject.extra

android {
  namespace = "dev.aungkyawpaing.ccdroidx.data"
  compileSdk = compileSdkVer

  defaultConfig {
    minSdk = minimumSdkVer

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
  coreLibraryDesugaring(libs.desugar.jdk.libs)
  implementation(project(":common"))

  implementation(libs.coroutine.core)
  implementation(libs.coroutine.android)
  testImplementation(libs.coroutine.test)
  androidTestImplementation(libs.coroutine.test)

  implementation(libs.timber)

  implementation(libs.androidx.appcompat)
  implementation(libs.androidx.core)

  implementation(libs.okhttp.client)
  implementation(libs.okhttp.logger)
  implementation(libs.simpleXml)
  testImplementation(libs.okhttp.mockWebServer)

  implementation(libs.androidx.sqlite)
  implementation(libs.sqldelight.android)
  implementation(libs.sqldelight.coroutine)
  testImplementation(libs.sqldelight.jvm)

  implementation(libs.moshi.core)
  implementation(libs.moshi.adapters)
  implementation(libs.moshi.kotlin)
  kapt(libs.moshi.codeGen)

  implementation(libs.dagger.hilt.android)
  implementation(libs.dagger.hilt.work)
  kapt(libs.dagger.hilt.compiler)
  kapt(libs.dagger.hilt.android.compiler)
  androidTestImplementation(libs.dagger.hilt.android.testing)
  kaptAndroidTest(libs.dagger.hilt.compiler)
  kaptAndroidTest(libs.dagger.hilt.android.compiler)
  testImplementation(libs.dagger.hilt.android.testing)
  kaptTest(libs.dagger.hilt.compiler)
  kaptTest(libs.dagger.hilt.android.compiler)

  testImplementation(libs.junit.jupiter.api)
  testRuntimeOnly(libs.junit.jupiter.engine)
  testImplementation(libs.junit.jupiter.params)
  testImplementation(libs.junit.junit4)
  testRuntimeOnly(libs.junit.jupiter.vintageEngine)
  androidTestImplementation(libs.junit.jupiter.api)
  androidTestImplementation(libs.androidJunit5.core)
  androidTestRuntimeOnly(libs.androidJunit5.runner)

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