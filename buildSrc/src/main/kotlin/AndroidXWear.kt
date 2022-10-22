import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.androidXWear() {
  implementation(AndroidXWear.wear)
  implementation(AndroidXWear.wear_remote_interactions)
  implementation(AndroidXWear.play_service_wearable)
}

object AndroidXWear {
  const val wear = "androidx.wear:wear:1.2.0"
  const val wear_remote_interactions = "androidx.wear:wear-remote-interactions:1.0.0"
  const val play_service_wearable = "com.google.android.gms:play-services-wearable:17.1.0"
}