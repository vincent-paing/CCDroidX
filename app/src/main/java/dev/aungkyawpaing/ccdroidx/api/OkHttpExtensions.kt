package dev.aungkyawpaing.ccdroidx.api

import okhttp3.Call
import okhttp3.Response

fun Call.executeOrThrow(): Response {

  val response = this.execute()

  if (response.isSuccessful.not()) {
    val errorString = response.body?.byteStream()?.bufferedReader()
      .use { it?.readText() }
    throw NetworkException(errorString, response.code)
  }

  return response
}
