import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.sqlDelight() {
  implementation(AndroidXSqlite.sqlite_ktx)

  implementation(SqlDelight.android_driver)
  implementation(SqlDelight.coroutine_extension)
  testImplementation(SqlDelight.jvm_driver)
}

object SqlDelight {
  private const val version = "1.5.3"

  const val gradle_plugin = "com.squareup.sqldelight:gradle-plugin:$version"
  const val android_driver = "com.squareup.sqldelight:android-driver:$version"
  const val coroutine_extension = "com.squareup.sqldelight:coroutines-extensions:$version"
  const val runtime = "com.squareup.sqldelight:runtime-common::$version"
  const val jvm_driver = "com.squareup.sqldelight:sqlite-driver:$version"
}

object AndroidXSqlite {
  private const val version = "2.2.0"

  const val sqlite = "androidx.sqlite:sqlite:$version"
  const val sqlite_framework = "androidx.sqlite:sqlite-framework:$version"
  const val sqlite_ktx = "androidx.sqlite:sqlite-ktx:$version"
}
