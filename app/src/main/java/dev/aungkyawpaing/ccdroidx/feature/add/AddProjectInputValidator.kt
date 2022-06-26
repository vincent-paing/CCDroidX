package dev.aungkyawpaing.ccdroidx.feature.add

import androidx.core.util.PatternsCompat
import dev.aungkyawpaing.ccdroidx.feature.add.feedurlvalidation.FeedUrlValidationResult
import dev.aungkyawpaing.ccdroidx.feature.add.passwordvalidation.PasswordValidationResult
import dev.aungkyawpaing.ccdroidx.feature.add.usernamevalidation.UsernameValidationResult
import javax.inject.Inject

class AddProjectInputValidator @Inject constructor() {

  fun validateFeedUrl(feedUrl: String): FeedUrlValidationResult {
    if (feedUrl.isEmpty()) return FeedUrlValidationResult.INCORRECT_EMPTY_TEXT

    if (!PatternsCompat.WEB_URL.matcher(feedUrl)
        .matches()
    ) return FeedUrlValidationResult.INCORRECT_INVALID_URL

    return FeedUrlValidationResult.CORRECT
  }

  fun validatePassword(password: String): PasswordValidationResult {
    if (password.isEmpty()) return PasswordValidationResult.INCORRECT_EMPTY_TEXT

    return PasswordValidationResult.CORRECT
  }

  fun validateUsername(username: String): UsernameValidationResult {
    if (username.isEmpty()) return UsernameValidationResult.INCORRECT_EMPTY_TEXT

    return UsernameValidationResult.CORRECT
  }


}