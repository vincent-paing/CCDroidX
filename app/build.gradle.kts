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

  testOptions{
    unitTests {
      isIncludeAndroidResources = true
    }
  }

  composeOptions {
    kotlinCompilerExtensionVersion  = "1.1.1"
  }


  kotlinOptions {
    jvmTarget = "1.8"
  }

  configurations.findByName("androidTestImplementation")?.run {
    exclude(group = "io.mockk", module = "mockk-agent-jvm")
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
  implementation("com.jakewharton.timber:timber:5.0.1")
  implementation("org.ocpsoft.prettytime:prettytime:5.0.2.Final")

  compose()
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

  daggerHilt()

  retrofit()
  coroutine()
  sqlDelight()

  junit5()
  androidXTest()
  mockK()
}