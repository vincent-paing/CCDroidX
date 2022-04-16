import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.retrofit() {
  implementation(OkHttp.client)
  implementation(OkHttp.logger)
  implementation("org.simpleframework:simple-xml:2.7.1")
  testImplementation(OkHttp.mock_web_server)
  testImplementation(OkHttp.mock_web_server)
}

object OkHttp {
  private const val version = "4.9.3"

  const val client = "com.squareup.okhttp3:okhttp:$version"
  const val logger = "com.squareup.okhttp3:logging-interceptor:$version"
  const val mock_web_server = "com.squareup.okhttp3:mockwebserver:$version"
}