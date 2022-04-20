import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.mockK() {

  testImplementation(Mockk.mockk)
  testImplementation(Mockk.mockk_agent_jvm)

  androidTestImplementation(Mockk.mockk_android)
  androidTestImplementation(Mockk.mockk_agent_jvm)
}


object Mockk {
  private const val version = "1.12.3"

  const val mockk = "io.mockk:mockk:$version"
  const val mockk_agent_jvm = "io.mockk:mockk-agent-jvm:$version"
  const val mockk_android = "io.mockk:mockk-android:$version"
}