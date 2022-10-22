import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.androidxProtoDataStore() {
  implementation(AndroidXDataStore.android)
  implementation(Wire.runtime)
}

object AndroidXDataStore {
  private const val version = "1.0.0"

  const val android = "androidx.datastore:datastore:$version"
  const val preference = "androidx.datastore:datastore-preferences:$version"
}

object Wire {
  private const val version = "4.3.0"

  const val runtime = "com.squareup.wire:wire-runtime:$version"
  const val gradle_plugin = "com.squareup.wire:wire-gradle-plugin:$version"
}