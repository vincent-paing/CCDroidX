package dev.aungkyawpaing.ccdroidx.data.api

import dev.aungkyawpaing.ccdroidx.common.BuildState
import dev.aungkyawpaing.ccdroidx.common.BuildStatus
import java.time.ZonedDateTime

data class ProjectResponse(
  val name: String,
  val activity: BuildState,
  val lastBuildStatus: BuildStatus,
  val lastBuildLabel: String?,
  val lastBuildTime: ZonedDateTime,
  val nextBuildTime: ZonedDateTime?,
  val webUrl: String,
  val feedUrl: String
)