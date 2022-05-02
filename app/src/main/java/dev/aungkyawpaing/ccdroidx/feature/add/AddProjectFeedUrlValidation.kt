package dev.aungkyawpaing.ccdroidx.feature.add

import androidx.core.util.PatternsCompat
import javax.inject.Inject

class AddProjectFeedUrlValidation @Inject constructor() {

  enum class ValidationResult {
    CORRECT,
    INCORRECT_EMPTY_TEXT,
    INCORRECT_INVALID_URL
  }

  fun validateProjectFeedUrl(feedUrl: String): ValidationResult {
    if (feedUrl.isEmpty()) return ValidationResult.INCORRECT_EMPTY_TEXT

    if (!PatternsCompat.WEB_URL.matcher(feedUrl)
        .matches()
    ) return ValidationResult.INCORRECT_INVALID_URL

    return ValidationResult.CORRECT
  }


}