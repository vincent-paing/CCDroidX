import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.androidXFragment() {
  implementation(AndroidXFragment.fragment_ktx)
  testImplementation(AndroidXFragment.fragment_testing)
  androidTestImplementation(AndroidXFragment.fragment_testing)
}


object AndroidXFragment {
  private const val version = "1.4.1"

  const val fragment = "androidx.fragment:fragment:$version"
  const val fragment_ktx = "androidx.fragment:fragment-ktx:$version"
  const val fragment_testing = "androidx.fragment:fragment-testing:$version"
}