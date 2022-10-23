import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.timber() {
  implementation("com.jakewharton.timber:timber:5.0.1")
}
