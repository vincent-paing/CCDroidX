package dev.aungkyawpaing.ccdroidx.feature.add.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.error
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import dev.aungkyawpaing.ccdroidx.R
import dev.aungkyawpaing.ccdroidx.feature.add.feedurlvalidation.FeedUrlValidationResult

@Composable
private fun getFeedUrlValidation(validation: FeedUrlValidationResult): String {
  return when (validation) {
    FeedUrlValidationResult.CORRECT -> {
      ""
    }
    FeedUrlValidationResult.INCORRECT_EMPTY_TEXT -> {
      stringResource(id = (R.string.error_feed_url_empty_text))
    }
    FeedUrlValidationResult.INCORRECT_INVALID_URL -> {
      stringResource(id = R.string.error_feed_url_invalid)
    }
  }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FeedUrlTextField(
  value: String,
  onValueChange: (String) -> Unit,
  isEnabled: Boolean,
  feedUrlValidation: FeedUrlValidationResult
) {

  val keyboardController = LocalSoftwareKeyboardController.current
  val isFeedUrlError = feedUrlValidation != FeedUrlValidationResult.CORRECT
  val error = getFeedUrlValidation(feedUrlValidation)

  OutlinedTextField(
    enabled = isEnabled,
    value = value,
    onValueChange = onValueChange,
    isError = isFeedUrlError,
    label = {
      Text(text = stringResource(R.string.feed_url))
    },
    singleLine = true,
    keyboardActions = KeyboardActions(
      onDone = {
        keyboardController?.hide()
      }
    ),
    modifier = Modifier.semantics {
      if (isFeedUrlError)
        error(error)
    }
  )
  if (isFeedUrlError) {
    Text(
      text = error,
      color = MaterialTheme.colorScheme.error,
      style = MaterialTheme.typography.bodySmall,
      modifier = Modifier.padding(start = 16.dp)
    )
  }
}