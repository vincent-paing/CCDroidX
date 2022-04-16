package dev.aungkyawpaing.ccdroidx.data

import java.time.ZonedDateTime

data class Project(
  val name: String,
  val activity: BuildState,
  val lastBuildStatus: BuildStatus,
  val lastBuildLabel: String?,
  val lastBuildTime: ZonedDateTime,
  val nextBuildTime: ZonedDateTime?,
  val webUrl: String
)

enum class BuildState {
  SLEEPING,
  BUILDING,
  CHECKING_MODIFICATIONS
}

enum class BuildStatus{
  SUCCESS,
  FAILURE,
  EXCEPTION,
  UNKNOWN
}