import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.junit5() {
  // (Required) Writing and executing Unit Tests on the JUnit Platform
  testImplementation(Jupiter.api)
  testRuntimeOnly(Jupiter.engine)

  // (Optional) If you need "Parameterized Tests"
  testImplementation(Jupiter.params)

  // (Optional) If you also have JUnit 4-based tests
  testImplementation("junit:junit:4.13.2")
  testRuntimeOnly(Jupiter.vintage_engine)

  androidTestImplementation(Jupiter.api)
  androidTestImplementation(AndroidJUnit5.android_test_core)
  androidTestRuntimeOnly(AndroidJUnit5.android_test_runner)
}

internal object Jupiter {
  private const val version = "5.9.2"

  const val api = "org.junit.jupiter:junit-jupiter-api:${version}"
  const val engine = "org.junit.jupiter:junit-jupiter-engine:${version}"
  const val params = "org.junit.jupiter:junit-jupiter-params:${version}"
  const val vintage_engine = "org.junit.vintage:junit-vintage-engine:${version}"
}

object AndroidJUnit5 {
  private const val version = "1.3.0"

  const val gradle_plugin = "de.mannodermaus.gradle.plugins:android-junit5:1.8.2.1"
  const val android_test_core = "de.mannodermaus.junit5:android-test-core:${version}"
  const val android_test_runner = "de.mannodermaus.junit5:android-test-runner:${version}"
}

