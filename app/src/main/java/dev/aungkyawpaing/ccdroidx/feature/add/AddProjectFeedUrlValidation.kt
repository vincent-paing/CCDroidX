package dev.aungkyawpaing.ccdroidx.feature.add

import androidx.core.util.PatternsCompat
import javax.inject.Inject

class AddProjectFeedUrlValidation @Inject constructor() {

  fun validateProjectFeedUrl(feedUrl: String): FeedUrlValidationResult {
    if (feedUrl.isEmpty()) return FeedUrlValidationResult.INCORRECT_EMPTY_TEXT

    if (!PatternsCompat.WEB_URL.matcher(feedUrl)
        .matches()
    ) return FeedUrlValidationResult.INCORRECT_INVALID_URL

    return FeedUrlValidationResult.CORRECT
  }


}