import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.androidXWorkManager() {
  implementation(AndroidXWorkManager.work_runtime)
  implementation(AndroidXWorkManager.gcm)
  implementation(AndroidXWorkManager.multi_process)
  androidTestImplementation(AndroidXWorkManager.testing)
}

object AndroidXWorkManager {
  private const val version = "2.8.1"

  const val work_runtime = "androidx.work:work-runtime-ktx:$version"
  const val gcm = "androidx.work:work-gcm:$version"
  const val multi_process = "androidx.work:work-multiprocess:$version"
  const val testing = "androidx.work:work-testing:$version"
}