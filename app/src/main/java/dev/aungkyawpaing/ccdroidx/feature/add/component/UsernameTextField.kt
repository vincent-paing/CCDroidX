package dev.aungkyawpaing.ccdroidx.feature.add.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.aungkyawpaing.ccdroidx.R
import dev.aungkyawpaing.ccdroidx.feature.add.usernamevalidation.UsernameValidationResult

@Composable
private fun getReasonFromUsernameValidation(validation: UsernameValidationResult): String {
  return when (validation) {
    UsernameValidationResult.CORRECT -> {
      ""
    }
    UsernameValidationResult.INCORRECT_EMPTY_TEXT -> {
      stringResource(id = R.string.error_username_empty_text)
    }
  }
}

@Composable
fun UsernameTextField(
  value: String,
  onValueChange: (String) -> Unit,
  isEnabled: Boolean,
  usernameValidation: UsernameValidationResult
) {

  val isUsernameValidationError = usernameValidation != UsernameValidationResult.CORRECT

  OutlinedTextField(
    value = value,
    onValueChange = onValueChange,
    enabled = isEnabled,
    label = {
      Text(text = stringResource(R.string.username))
    },
    placeholder = {
      Text(text = "John Doe")
    },
    isError = isUsernameValidationError,
    singleLine = true,
    modifier = Modifier.padding(top = 8.dp)
  )
  if (isUsernameValidationError) {
    Text(
      text = getReasonFromUsernameValidation(usernameValidation),
      color = MaterialTheme.colorScheme.error,
      style = MaterialTheme.typography.bodySmall,
      modifier = Modifier.padding(start = 16.dp)
    )
  }
}