package dev.aungkyawpaing.ccdroidx.feature.add.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import dev.aungkyawpaing.ccdroidx.R
import dev.aungkyawpaing.ccdroidx.feature.add.passwordvalidation.PasswordValidationResult

@Composable
private fun getReasonFromPasswordValidation(validation: PasswordValidationResult): String {
  return when (validation) {
    PasswordValidationResult.CORRECT -> {
      ""
    }
    PasswordValidationResult.INCORRECT_EMPTY_TEXT -> {
      return stringResource(R.string.error_password_url_empty_text)
    }
  }
}

@Composable
fun PasswordTextField(
  value: String,
  onValueChange: (String) -> Unit,
  isEnabled: Boolean,
  passwordValidationResult: PasswordValidationResult
) {

  var passwordVisible by rememberSaveable { mutableStateOf(false) }
  val isPasswordValidationError = passwordValidationResult != PasswordValidationResult.CORRECT

  OutlinedTextField(
    value = value, onValueChange = onValueChange,
    enabled = isEnabled,
    label = {
      Text(text = stringResource(R.string.password))
    },
    trailingIcon = {
      IconButton(onClick = {
        passwordVisible = !passwordVisible
      }) {
        if (passwordVisible) {
          Icon(Icons.Outlined.VisibilityOff, contentDescription = "Hide password")
        } else {
          Icon(Icons.Outlined.Visibility, contentDescription = "Show password")
        }
      }
    },
    visualTransformation = if (passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
    singleLine = true,
    isError = isPasswordValidationError,
    modifier = Modifier.padding(top = 8.dp)
  )
  if (isPasswordValidationError) {
    Text(
      text = getReasonFromPasswordValidation(passwordValidationResult),
      color = MaterialTheme.colorScheme.error,
      style = MaterialTheme.typography.bodySmall,
      modifier = Modifier.padding(start = 16.dp)
    )
  }
}