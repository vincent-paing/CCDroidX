package dev.aungkyawpaing.ccdroidx.feature.sync

import java.time.ZonedDateTime

data class LastSyncedStatus(
  val lastSyncedDateTime: ZonedDateTime,
  val lastSyncedState: LastSyncedState,
  val errorCode: Long? = null
)

enum class LastSyncedState {
  SYNCING, SUCCESS, FAILED
}