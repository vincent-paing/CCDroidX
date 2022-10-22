import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.coroutine() {
  implementation(Coroutine.core)
  implementation(Coroutine.android)
  testImplementation(Coroutine.test)
  androidTestImplementation(Coroutine.test)
}

fun DependencyHandler.coroutinePlayService() {
  implementation(Coroutine.integration_play_service)
}

object Coroutine {
  private const val version = "1.6.4"

  const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
  const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
  const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"

  const val integration_play_service = "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:$version"
}
