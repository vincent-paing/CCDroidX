package dev.aungkyawpaing.ccdroidx.api

import dev.aungkyawpaing.ccdroidx.data.Project
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

class FetchProject @Inject constructor(
  private val client: OkHttpClient
) {

  fun requestForProjectList(url: String): List<Project> {
    val request = Request.Builder()
      .url(url)
      .build();

    client.newCall(request).executeOrThrow().use { response ->
      return CCTrayParser.parseResponse(response ?: throw NetworkException())
    }
  }
}