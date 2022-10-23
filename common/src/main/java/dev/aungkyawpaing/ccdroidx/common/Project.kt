package dev.aungkyawpaing.ccdroidx.common

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.ZonedDateTime

@Parcelize
data class Project(
  val id: Long,
  val name: String,
  val activity: BuildState,
  val lastBuildStatus: BuildStatus,
  val lastBuildLabel: String?,
  val lastBuildTime: ZonedDateTime,
  val nextBuildTime: ZonedDateTime?,
  val webUrl: String,
  val feedUrl: String,
  val isMuted: Boolean,
  val mutedUntil: ZonedDateTime?,
  val authentication: Authentication? = null
) : Parcelable

@Parcelize
data class Authentication(
  val username: String,
  val password: String
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