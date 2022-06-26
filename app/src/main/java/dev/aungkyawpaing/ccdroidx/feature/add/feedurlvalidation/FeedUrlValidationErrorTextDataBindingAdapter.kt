package dev.aungkyawpaing.ccdroidx.feature.add.feedurlvalidation

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import dev.aungkyawpaing.ccdroidx.R


object FeedUrlValidationErrorTextDataBindingAdapter {

  @BindingAdapter("feedValidationErrorText")
  @JvmStatic
  fun setErrorText(layout: TextInputLayout, validation: FeedUrlValidationResult) {
    when (validation) {
      FeedUrlValidationResult.CORRECT -> {
        layout.error = null
      }
      FeedUrlValidationResult.INCORRECT_EMPTY_TEXT -> {
        layout.error = layout.context.getString(R.string.error_feed_url_empty_text)
      }
      FeedUrlValidationResult.INCORRECT_INVALID_URL -> {
        layout.error = layout.context.getString(R.string.error_feed_url_invalid)
      }
    }
  }

}