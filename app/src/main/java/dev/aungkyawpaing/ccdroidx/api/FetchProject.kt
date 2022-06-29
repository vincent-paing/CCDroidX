package dev.aungkyawpaing.ccdroidx.api

import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.Request
import timber.log.Timber
import javax.inject.Inject

class FetchProject @Inject constructor(
  private val client: OkHttpClient
) {

  fun requestForProjectList(
    url: String,
    username: String? = null,
    password: String? = null
  ): List<ProjectResponse> {
    val request = try {
      Request.Builder()
        .url(url)
        .also {
          if (username != null && password != null) {
            it.addHeader("Authorization", Credentials.basic(username, password))
          }
        }
        .build()
    } catch (exception: IllegalArgumentException) {
      Timber.e(exception)
      throw InvalidUrlException()
    }

    client.newCall(request).executeOrThrow().use { response ->
      try {
        return CCTrayParser.parseResponse(response)
      } catch (exception: Exception) {
        Timber.e(exception)
        throw UnsupportedServerException()
      }
    }
  }
}