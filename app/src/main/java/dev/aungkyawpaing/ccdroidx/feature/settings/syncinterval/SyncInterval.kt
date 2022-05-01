package dev.aungkyawpaing.ccdroidx.feature.settings.syncinterval

import java.time.Duration
import java.time.temporal.ChronoUnit

data class SyncInterval(
  val value: Int,
  val timeUnit: SyncIntervalTimeUnit
) {

  fun asDuration(): Duration {
    return Duration.of(
      value.toLong(), when (timeUnit) {
        SyncIntervalTimeUnit.MINUTES -> ChronoUnit.MINUTES
        SyncIntervalTimeUnit.HOUR -> ChronoUnit.HOURS
        SyncIntervalTimeUnit.DAY -> ChronoUnit.DAYS
      }
    )
  }
}

enum class SyncIntervalTimeUnit {
  MINUTES,
  HOUR,
  DAY
}