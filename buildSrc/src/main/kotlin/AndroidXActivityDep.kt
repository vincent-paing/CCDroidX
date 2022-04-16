import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.androidXActivity() {
  implementation(AndroidXActivity.activity_ktx)
}


object AndroidXActivity {
  private const val version = "1.4.0"

  const val activity = "androidx.activity:activity:$version"
  const val activity_ktx = "androidx.activity:activity-ktx:$version"
}
