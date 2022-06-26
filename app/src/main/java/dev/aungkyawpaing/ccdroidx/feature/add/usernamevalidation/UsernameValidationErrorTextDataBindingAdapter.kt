package dev.aungkyawpaing.ccdroidx.feature.add.usernamevalidation

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import dev.aungkyawpaing.ccdroidx.R

object UsernameValidationErrorTextDataBindingAdapter {

  @BindingAdapter("usernameValidationErrorText")
  @JvmStatic
  fun setErrorText(layout: TextInputLayout, validation: UsernameValidationResult) {
    when (validation) {
      UsernameValidationResult.CORRECT -> {
        layout.error = null
      }
      UsernameValidationResult.INCORRECT_EMPTY_TEXT -> {
        layout.error = layout.context.getString(R.string.error_username_empty_text)
      }
    }
  }

}