import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.permissionFlow() {
  implementation(AndroidXFragment.fragment_ktx)
  testImplementation(AndroidXFragment.fragment_testing)
  androidTestImplementation(AndroidXFragment.fragment_testing)
}


object PermissionFlow {
  private const val version = "1.0.0"

  const val android = "dev.shreyaspatil.permission-flow:permission-flow-android:$version"
  const val compose = "dev.shreyaspatil.permission-flow:permission-flow-compose:$version"
}