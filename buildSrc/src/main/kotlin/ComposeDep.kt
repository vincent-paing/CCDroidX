import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.compose() {
  // (Required) Writing and executing Unit Tests on the JUnit Platform
  implementation(AndroidXActivity.activityCompose)

  implementation(Compose.ui)
  implementation(Compose.ui_tooling)
  implementation(Compose.ui_tooling_preview)
  implementation(Compose.foundation)
  implementation(Compose.animation)

  implementation(Compose.view_model)
  implementation(Compose.live_data)

  implementation(Compose.constraint_layout)

  implementation(Compose.material_icons_extended)
  implementation(Compose.material_theme_adapter)

  testImplementation(Compose.ui_test)
  androidTestImplementation(Compose.ui_test)
  debugImplementation(Compose.ui_test_manifest)
}

fun DependencyHandler.composePhone() {
  implementation(Compose.material)
}

fun DependencyHandler.composeWear() {
  implementation(ComposeWear.material)
  implementation(ComposeWear.foundation)
  implementation(ComposeWear.navigation)
}


internal object ComposeWear {
  private const val version = "1.1.2"

  const val material = "androidx.wear.compose:compose-material:$version"
  const val foundation = "androidx.wear.compose:compose-foundation:$version"
  const val navigation = "androidx.wear.compose:compose-navigation:$version"
}

internal object Compose {
  private const val version = "1.4.0"

  const val ui = "androidx.compose.ui:ui:$version"
  const val foundation = "androidx.compose.foundation:foundation:$version"

  const val material = "androidx.compose.material3:material3:1.0.1"
  const val material_icons_extended = "androidx.compose.material:material-icons-extended:$version"
  const val animation = "androidx.compose.animation:animation:$version"

  const val ui_tooling = "androidx.compose.ui:ui-tooling:$version"
  const val ui_tooling_preview = "androidx.compose.ui:ui-tooling-preview:$version"

  const val view_model = "androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1"
  const val live_data = "androidx.compose.runtime:runtime-livedata:$version"
  const val material_theme_adapter = "com.google.android.material:compose-theme-adapter-3:1.1.1"
  const val constraint_layout = "androidx.constraintlayout:constraintlayout-compose:1.0.1"

  const val ui_test = "androidx.compose.ui:ui-test-junit4:$version"
  const val ui_test_manifest = "androidx.compose.ui:ui-test-manifest:$version"
}