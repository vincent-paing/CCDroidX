val compileSdkVer by extra { 34 }
val targetSdkVer by extra { 34 }
val minimumSdkVer by extra { 28 }

private val versionMajor = 1
private val versionMinor = 2
private val versionPatch = 1
private val versionBuild = 0
val versionNameConfig by extra { "$versionMajor.$versionMinor.$versionPatch" }
val versionCodeConfig by extra {   versionMajor * 1000000 + versionMinor * 10000 + versionPatch * 100 + versionBuild * 10 }
val wearVersionCodeConfig by extra {  versionMajor * 1000000 + versionMinor * 10000 + versionPatch * 100 + versionBuild * 10 + 1 }


plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.android.library) apply false
  alias(libs.plugins.kotlin.android) apply false
  alias(libs.plugins.kotlin.kapt) apply false
  alias(libs.plugins.kotlin.pracelize) apply false
  alias(libs.plugins.dagger.hilt) apply false
}