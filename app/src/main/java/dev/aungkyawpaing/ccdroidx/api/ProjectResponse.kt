package dev.aungkyawpaing.ccdroidx.api

import android.os.Parcelable
import dev.aungkyawpaing.ccdroidx.data.BuildState
import dev.aungkyawpaing.ccdroidx.data.BuildStatus
import kotlinx.parcelize.Parcelize
import java.time.ZonedDateTime

@Parcelize
data class ProjectResponse(
  val name: String,
  val activity: BuildState,
  val lastBuildStatus: BuildStatus,
  val lastBuildLabel: String?,
  val lastBuildTime: ZonedDateTime,
  val nextBuildTime: ZonedDateTime?,
  val webUrl: String,
  val feedUrl: String
) : Parcelable