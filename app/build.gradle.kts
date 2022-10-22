plugins {
  id("com.android.application")
  kotlin("android")
  kotlin("kapt")
  id("kotlin-parcelize")
  id("com.squareup.sqldelight")
  id("androidx.navigation.safeargs.kotlin")
  id("dagger.hilt.android.plugin")
  id("com.squareup.wire")
  id("de.mannodermaus.android-junit5")
  id("com.google.gms.google-services")
  id("com.google.firebase.crashlytics")
}

val ENV = System.getenv()

android {
  compileSdk = BuildConfig.compileSdk

  defaultConfig {
    applicationId = "dev.aungkyawpaing.ccdroidx"
    minSdk = BuildConfig.minSdk
    targetSdk = BuildConfig.targetSdk
    versionCode = BuildConfig.versionCode
    versionName = BuildConfig.versionName
    resourceConfigurations += setOf("en")
    setProperty("archivesBaseName", "ccdroidx-${BuildConfig.versionName}")

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    testInstrumentationRunnerArguments["runnerBuilder"] =
      "de.mannodermaus.junit5.AndroidJUnit5Builder"

    buildFeatures {
      viewBinding = true
      dataBinding = true
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

wire {
  kotlin {
  }
}

dependencies {
  coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")
  implementation(project(":common"))
  implementation(project(":data"))

  timber()
  implementation("org.ocpsoft.prettytime:prettytime:5.0.2.Final")

  compose()
  composePhone()

  implementation(AndroidXConstraintLayout.constraint_layout)
  androidX()
  androidXArch()
  androidXActivity()
  androidXFragment()
  androidXNavigation()
  androidXWorkManager()
  implementation("androidx.browser:browser:1.4.0")

  androidxProtoDataStore()
  implementation("androidx.preference:preference-ktx:1.2.0")

  implementation(Material.material)

  // Firebase
  implementation(project.dependencies.platform("com.google.firebase:firebase-bom:30.4.1"))
  implementation("com.google.firebase:firebase-analytics-ktx")
  implementation("com.google.firebase:firebase-crashlytics-ktx")

  daggerHilt()

  coroutine()
  retrofit()
  sqlDelight()

  junit5()
  androidXTest()
  mockK()
}