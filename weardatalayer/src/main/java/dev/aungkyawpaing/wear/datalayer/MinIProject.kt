package dev.aungkyawpaing.wear.datalayer

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MiniProject(
  @Json(name = "name") val name: String,
  @Json(name = "isSuccess") val isSuccess: Boolean
)