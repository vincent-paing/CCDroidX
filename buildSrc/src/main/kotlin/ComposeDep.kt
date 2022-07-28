import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.compose() {
  // (Required) Writing and executing Unit Tests on the JUnit Platform
  implementation(Compose.activityCompose)
  implementation(Compose.material)
  implementation(Compose.material_icons_extended)
  implementation(Compose.animation)
  implementation(Compose.tooling)
  implementation(Compose.view_model)
  implementation(Compose.live_data)
  implementation(Compose.material_theme_adapter)
  implementation(Compose.constraint_layout)

  testImplementation(Compose.ui_test)
  androidTestImplementation(Compose.ui_test)
  debugImplementation(Compose.ui_test_manifest)
}

internal object Compose {
  private const val version = "1.1.1"

  const val activityCompose = "androidx.activity:activity-compose:1.4.0"
  const val material = "androidx.compose.material3:material3:1.0.0-alpha14"
  const val material_icons_extended = "androidx.compose.material:material-icons-extended:$version"
  const val animation = "androidx.compose.animation:animation:$version"
  const val tooling = "androidx.compose.ui:ui-tooling:$version"
  const val view_model = "androidx.lifecycle:lifecycle-viewmodel-compose:2.4.1"
  const val live_data =  "androidx.compose.runtime:runtime-livedata:$version"
  const val material_theme_adapter = "com.google.android.material:compose-theme-adapter-3:1.0.11"
  const val constraint_layout = "androidx.constraintlayout:constraintlayout-compose:1.0.1"

  const val ui_test = "androidx.compose.ui:ui-test-junit4:$version"
  const val ui_test_manifest = "androidx.compose.ui:ui-test-manifest:$version"


}