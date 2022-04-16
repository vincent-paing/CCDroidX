import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.retrofit() {
  implementation(Retrofit.core)
  implementation(Retrofit.simplexml_converter)

  implementation(OkHttp.client)
  implementation(OkHttp.logger)
  testImplementation(OkHttp.mock_web_server)
  testImplementation(OkHttp.mock_web_server)
}

object Retrofit {
  private const val version = "2.9.0"

  const val core = "com.squareup.retrofit2:retrofit:$version"
  const val simplexml_converter = "com.squareup.retrofit2:converter-simplexml:$version"
}

object OkHttp {
  private const val version = "4.9.3"

  const val client = "com.squareup.okhttp3:okhttp:$version"
  const val logger = "com.squareup.okhttp3:logging-interceptor:$version"
  const val mock_web_server = "com.squareup.okhttp3:mockwebserver:$version"
}