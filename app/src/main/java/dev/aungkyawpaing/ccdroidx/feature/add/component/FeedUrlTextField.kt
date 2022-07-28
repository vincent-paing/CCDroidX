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

@Composable
fun FeedUrlTextField(
  value: String,
  onValueChange: (String) -> Unit,
  isDisabled: Boolean,
  feedUrlValidation: FeedUrlValidationResult
) {

  val isFeedUrlError = feedUrlValidation != FeedUrlValidationResult.CORRECT

  OutlinedTextField(
    enabled = isDisabled,
    label = {
      Text(text = stringResource(R.string.feed_url))
    },
    placeholder = {
      Text(text = "https://your_ci.com/cc.xml")
    },
    singleLine = true,
    isError = isFeedUrlError,
    value = value, onValueChange = onValueChange
  )
  if (isFeedUrlError) {
    Text(
      text = getFeedUrlValidation(feedUrlValidation),
      color = MaterialTheme.colorScheme.error,
      style = MaterialTheme.typography.bodySmall,
      modifier = Modifier.padding(start = 16.dp)
    )
  }
}