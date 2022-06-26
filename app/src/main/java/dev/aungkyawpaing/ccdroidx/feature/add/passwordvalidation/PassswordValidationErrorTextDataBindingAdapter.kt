package dev.aungkyawpaing.ccdroidx.feature.add.passwordvalidation

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import dev.aungkyawpaing.ccdroidx.R

object PassswordValidationErrorTextDataBindingAdapter {

  @BindingAdapter("passwordValidationErrorText")
  @JvmStatic
  fun setErrorText(layout: TextInputLayout, validation: PasswordValidationResult) {
    when (validation) {
      PasswordValidationResult.CORRECT -> {
        layout.error = null
      }
      PasswordValidationResult.INCORRECT_EMPTY_TEXT -> {
        layout.error = layout.context.getString(R.string.error_password_url_empty_text)
      }
    }
  }

}