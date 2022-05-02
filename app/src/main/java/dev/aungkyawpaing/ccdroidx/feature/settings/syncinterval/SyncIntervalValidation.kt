package dev.aungkyawpaing.ccdroidx.feature.settings.syncinterval

import javax.inject.Inject

enum class SyncIntervalValidationResult {
  CORRECT,
  INCORRECT_EMPTY_VALUE,
  INCORRECT_NON_INTEGER,
  INCORRECT_LESS_THAN_MINIMUM_15_MINUTES
}

class SyncIntervalValidation @Inject constructor() {

  fun validateProjectFeedUrl(
    value: String?,
    timeUnit: SyncIntervalTimeUnit?
  ): SyncIntervalValidationResult {

    if (value.isNullOrEmpty() || timeUnit == null) return SyncIntervalValidationResult.INCORRECT_EMPTY_VALUE

    val intValue = value.toIntOrNull()
      ?: return SyncIntervalValidationResult.INCORRECT_NON_INTEGER

    if (timeUnit == SyncIntervalTimeUnit.MINUTES && intValue < 15) {
      return SyncIntervalValidationResult.INCORRECT_LESS_THAN_MINIMUM_15_MINUTES
    }

    return SyncIntervalValidationResult.CORRECT
  }


}