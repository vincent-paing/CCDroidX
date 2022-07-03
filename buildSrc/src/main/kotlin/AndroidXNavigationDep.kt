import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.androidXNavigation() {
  implementation(AndroidXNavigation.fragment_ktx)
  implementation(AndroidXNavigation.ui_ktx)
  testImplementation(AndroidXNavigation.testing)
  androidTestImplementation(AndroidXNavigation.testing)
}


object AndroidXNavigation {
  private const val version = "2.4.2"

  const val gradle_plugin = "androidx.navigation:navigation-safe-args-gradle-plugin:$version"

  const val common = "androidx.navigation:navigation-common:$version"
  const val common_ktx = "androidx.navigation:navigation-common-ktx:$version"
  const val fragment = "androidx.navigation:navigation-fragment:$version"
  const val fragment_ktx = "androidx.navigation:navigation-fragment-ktx:$version"
  const val runtime = "androidx.navigation:navigation-runtime:$version"
  const val runtime_ktx = "androidx.navigation:navigation-runtime-ktx:$version"
  const val safe_args_generator = "androidx.navigation:navigation-safe-args-generator:$version"
  const val safe_args_plugin = "androidx.navigation:navigation-safe-args-gradle-plugin:$version"
  const val testing = "androidx.navigation:navigation-testing:$version"
  const val ui = "androidx.navigation:navigation-ui:$version"
  const val ui_ktx = "androidx.navigation:navigation-ui-ktx:$version"
}