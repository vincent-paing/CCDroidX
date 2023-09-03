package dev.aungkyawpaing.ccdroidx.feature.settings.syncinterval

import javax.inject.Inject

enum class SyncIntervalValidationResult {
  CORRECT,
  INCORRECT_EMPTY_VALUE,
  INCORRECT_NON_INTEGER,
  INCORRECT_LESS_THAN_MINIMUM_15_MINUTES
}

class SyncIntervalValidation @Inject constructor() {

  fun validateSyncInterval(
    value: String
  ): SyncIntervalValidationResult {

    if (value.isEmpty()) return SyncIntervalValidationResult.INCORRECT_EMPTY_VALUE

    val longValue = value.toLongOrNull()
      ?: return SyncIntervalValidationResult.INCORRECT_NON_INTEGER

    if (longValue < 15) {
      return SyncIntervalValidationResult.INCORRECT_LESS_THAN_MINIMUM_15_MINUTES
    }

    return SyncIntervalValidationResult.CORRECT
  }


}