import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.coroutine() {
  implementation(Coroutine.core)
  implementation(Coroutine.android)
  testImplementation(Coroutine.test)
  androidTestImplementation(Coroutine.test)
}

object Coroutine {
  private const val version = "1.6.1"

  const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
  const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
  const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
}

