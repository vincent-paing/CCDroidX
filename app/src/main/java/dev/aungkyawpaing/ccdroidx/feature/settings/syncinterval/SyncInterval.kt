package dev.aungkyawpaing.ccdroidx.feature.settings.syncinterval

data class SyncInterval(
  val value: Int,
  val timeUnit: SupportedTimeUnit
)

enum class SupportedTimeUnit {
  MINUTES,
  HOUR,
  DAY
}