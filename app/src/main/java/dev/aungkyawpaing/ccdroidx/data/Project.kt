package dev.aungkyawpaing.ccdroidx.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.ZonedDateTime

@Parcelize
data class Project(
  val name: String,
  val activity: BuildState,
  val lastBuildStatus: BuildStatus,
  val lastBuildLabel: String?,
  val lastBuildTime: ZonedDateTime,
  val nextBuildTime: ZonedDateTime?,
  val webUrl: String
) : Parcelable

@Parcelize
enum class BuildState : Parcelable {
  SLEEPING,
  BUILDING,
  CHECKING_MODIFICATIONS
}

@Parcelize
enum class BuildStatus : Parcelable {
  SUCCESS,
  FAILURE,
  EXCEPTION,
  UNKNOWN
}