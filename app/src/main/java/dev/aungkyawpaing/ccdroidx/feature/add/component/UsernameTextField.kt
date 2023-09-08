package dev.aungkyawpaing.ccdroidx.feature.add.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.error
import androidx.compose.ui.semantics.semantics
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
  val error = getReasonFromUsernameValidation(usernameValidation)

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
    supportingText = {
      Text(
        text = error,
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier.padding(start = 16.dp)
      )
    },
    isError = isUsernameValidationError,
    singleLine = true,
    modifier = Modifier
      .fillMaxWidth()
      .semantics {
        if (isUsernameValidationError)
          error(error)
      }
  )
}