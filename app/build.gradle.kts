import androidx.navigation.safe.args.generator.ext.capitalize
import com.android.build.api.dsl.ManagedVirtualDevice
import com.google.devtools.ksp.gradle.KspTaskJvm

plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.ksp)
  alias(libs.plugins.kotlin.pracelize)
  alias(libs.plugins.androidx.navigation.safeargs)
  alias(libs.plugins.dagger.hilt)
  alias(libs.plugins.wire)
  alias(libs.plugins.android.junit5)
  alias(libs.plugins.google.services)
  alias(libs.plugins.firebase.crashlytics)
}

val ENV = System.getenv()
val compileSdkVer: Int by rootProject.extra
val targetSdkVer: Int by rootProject.extra
val minimumSdkVer: Int by rootProject.extra
val versionNameConfig: String by rootProject.extra
val versionCodeConfig: Int by rootProject.extra

android {
  compileSdk = compileSdkVer

  defaultConfig {
    applicationId = "dev.aungkyawpaing.ccdroidx"
    minSdk = minimumSdkVer
    targetSdk = targetSdkVer
    versionCode = versionCodeConfig
    versionName = versionNameConfig
    resourceConfigurations += setOf("en")
    setProperty("archivesBaseName", "ccdroidx-${versionNameConfig}")

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    testInstrumentationRunnerArguments["runnerBuilder"] =
      "de.mannodermaus.junit5.AndroidJUnit5Builder"

    buildFeatures {
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
    getByName("debug") {
      isMinifyEnabled = false
      isDebuggable = true
      versionNameSuffix = "-debug"
      applicationIdSuffix = ".debug"
    }

    getByName("release") {
      isMinifyEnabled = true
      isDebuggable = false
      signingConfig = signingConfigs.getByName("release")
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }

  flavorDimensions += "version"
  productFlavors {
    create("floss") {}
    create("full") {}
  }


  compileOptions {
    isCoreLibraryDesugaringEnabled = true
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  testOptions {
    managedDevices {
      devices {
        maybeCreate<ManagedVirtualDevice>("Pixel5").apply {
          device = "Pixel 5"
          // Prefer to use latest image but aosp-atd build is only available for 30 as of now
          apiLevel = 30
          systemImageSource = "aosp-atd"
        }
        groups {
          maybeCreate("testDevices").apply {
            targetDevices.add(devices["Pixel5"])
          }
        }

      }
    }

    unitTests {
      isIncludeAndroidResources = true
    }
  }

  composeOptions {
    kotlinCompilerExtensionVersion = "1.4.4"
  }

  kotlinOptions {
    jvmTarget = "17"
  }

  namespace = "dev.aungkyawpaing.ccdroidx"

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

wire {
  kotlin {
  }
}

dependencies {
  coreLibraryDesugaring(libs.desugar.jdk.libs)
  implementation(project(":common"))
  implementation(project(":data"))
  "fullImplementation"(project(":weardatalayer"))

  implementation(libs.timber)
  implementation(libs.prettytime)

  implementation(libs.compose.ui)
  implementation(libs.compose.ui.tooling)
  implementation(libs.compose.ui.tooling.preview)
  testImplementation(libs.compose.ui.test)
  androidTestImplementation(libs.compose.ui.test)
  debugImplementation(libs.compose.ui.test.manifest)
  implementation(libs.compose.foundation)
  implementation(libs.compose.animation)
  implementation(libs.compose.material3)
  implementation(libs.compose.material.icon.extended)
  implementation(libs.compose.constraintLayout)
  implementation(libs.compose.liveData)
  implementation(libs.compose.viewModel)

  implementation(libs.accompanist)

  implementation(libs.androidx.hilt.navigation)
  implementation(libs.compose.destinations.core)
  ksp(libs.compose.destinations.ksp)

  implementation(libs.androidx.constraintLayout)
  implementation(libs.androidx.appcompat)
  implementation(libs.androidx.core)

  implementation(libs.androidx.activity)
  implementation(libs.androidx.fragment)
  testImplementation(libs.androidx.fragment.testing)
  androidTestImplementation(libs.androidx.fragment.testing)

  implementation(libs.androidx.lifecycle.viewModel)
  implementation(libs.androidx.lifecycle.liveData)
  implementation(libs.androidx.lifecycle.extensions)
  implementation(libs.androidx.lifecycle.java8)
  implementation(libs.androidx.lifecycle.service)
  testImplementation(libs.androidx.arch.testing)

  implementation(libs.androidx.navigation.fragment)
  implementation(libs.androidx.navigation.ui)
  testImplementation(libs.androidx.navigation.testing)
  androidTestImplementation(libs.androidx.navigation.testing)

  implementation(libs.androidx.work.runtime)
  "fullImplementation"(libs.androidx.work.gcm)
  implementation(libs.androidx.work.multiProcess)
  androidTestImplementation(libs.androidx.work.testing)

  implementation(libs.androidx.browser)

  implementation(libs.androidx.dataStore)
  implementation(libs.androidx.dataStore.preference)
  implementation(libs.wire)
  implementation(libs.androidx.preference)

  implementation(libs.material)

  // Firebase
  "fullImplementation"(platform(libs.firebase.bom))
  "fullImplementation"(libs.firebase.analytics)
  "fullImplementation"(libs.firebase.crashlytics)

  implementation(libs.permissionFlow.android)

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

  implementation(libs.coroutine.core)
  implementation(libs.coroutine.android)
  testImplementation(libs.coroutine.test)
  androidTestImplementation(libs.coroutine.test)

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
  androidTestImplementation(libs.mockk.android)
}

androidComponents {
  onVariants { variant ->
    // https://github.com/square/wire/issues/2335
    val buildType = variant.buildType.toString()
    val flavor = variant.flavorName.toString()
    tasks.withType<KspTaskJvm> {
      if (name.contains(buildType, ignoreCase = true) && name.contains(flavor, ignoreCase = true)) {
        dependsOn("generate${flavor.capitalize()}${buildType.capitalize()}Protos")
      }
    }
  }
}