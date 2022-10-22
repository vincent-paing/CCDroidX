import org.gradle.api.artifacts.dsl.DependencyHandler


fun DependencyHandler.androidX() {
  implementation(AndroidXAppCompat.app_compat)
  implementation(AndroidXCore.core_ktx)
}

object AndroidXAppCompat {
  const val app_compat = "androidx.appcompat:appcompat:1.4.1"
}

object AndroidXConstraintLayout {
  private const val version = "2.1.3"

  const val constraint_layout = "androidx.constraintlayout:constraintlayout:$version"
}

object AndroidXCore {
  private const val version = "1.7.0"

  const val core = "androidx.core:core:$version"
  const val core_ktx = "androidx.core:core-ktx:$version"
}