package dev.aungkyawpaing.ccdroidx.feature.settings.syncinterval

data class SyncInterval(
  val value: Int,
  val timeUnit: SyncIntervalTimeUnit
)

enum class SyncIntervalTimeUnit {
  MINUTES,
  HOUR,
  DAY
}