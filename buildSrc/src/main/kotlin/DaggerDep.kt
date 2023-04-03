import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.daggerAndroid() {
  implementation(Dagger.core)
  implementation(Dagger.android_core)
  implementation(Dagger.android_support)
  kapt(Dagger.compiler)
  kapt(Dagger.android_processor)
}

fun DependencyHandler.daggerJvm() {
  implementation(Dagger.core)
  kapt(Dagger.compiler)
}

fun DependencyHandler.daggerHilt() {
  implementation(DaggerHilt.android)
  implementation(DaggerHilt.work)
  kapt(DaggerHilt.compiler)
  kapt(DaggerHilt.androidCompiler)

  androidTestImplementation(DaggerHilt.android_testing)
  kaptAndroidTest(DaggerHilt.compiler)
  kaptAndroidTest(DaggerHilt.androidCompiler)

  testImplementation(DaggerHilt.android_testing)
  kaptTest(DaggerHilt.compiler)
  kaptTest(DaggerHilt.androidCompiler)
}

object DaggerHilt {
  private const val version = "2.44"

  const val android = "com.google.dagger:hilt-android:$version"
  const val androidCompiler = "com.google.dagger:hilt-android-compiler:$version"
  const val compiler = "com.google.dagger:hilt-compiler:$version"
  const val work = "androidx.hilt:hilt-work:1.0.0"
  const val android_testing = "com.google.dagger:hilt-android-testing:$version"
  const val gradle_plugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
}

object Dagger {
  private const val version = "2.44"

  const val core = "com.google.dagger:dagger:$version"
  const val compiler = "com.google.dagger:dagger-compiler:$version"
  const val android_core = "com.google.dagger:dagger-android:$version"
  const val android_support = "com.google.dagger:dagger-android-support:$version"
  const val android_processor = "com.google.dagger:dagger-android-processor:$version"
}