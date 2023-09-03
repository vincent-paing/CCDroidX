package dev.aungkyawpaing.ccdroidx.feature.settings.syncinterval

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.error
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import com.google.accompanist.themeadapter.material3.Mdc3Theme
import dev.aungkyawpaing.ccdroidx.R
import dev.aungkyawpaing.ccdroidx.feature.settings.Settings
import dev.aungkyawpaing.ccdroidx.feature.settings.settingsDataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@Composable
private fun getValidationErrorString(validationResult: SyncIntervalValidationResult): String? {
  when (validationResult) {
    SyncIntervalValidationResult.CORRECT -> {
      return null
    }

    SyncIntervalValidationResult.INCORRECT_EMPTY_VALUE -> {
      return stringResource(R.string.error_interval_empty_text)
    }

    SyncIntervalValidationResult.INCORRECT_NON_INTEGER -> {
      return stringResource(R.string.error_interval_non_integer)
    }

    SyncIntervalValidationResult.INCORRECT_LESS_THAN_MINIMUM_15_MINUTES -> {
      return stringResource(R.string.error_interval_less_than_minimum)
    }
  }
}


@Composable
fun SyncIntervalInputDialog(
  onDismissRequest: () -> Unit,
) {
  val scope = rememberCoroutineScope()
  val dataStore = LocalContext.current.settingsDataStore
  val syncIntervalValidation = SyncIntervalValidation()
  var value by rememberSaveable { mutableStateOf<String?>(null) }


  LaunchedEffect(Unit) {
    value = (dataStore.data.firstOrNull()?.get(Settings.KEY_SYNC_INTERVAL)
      ?: Settings.DEFAULT_SYNC_INTERVAL.toMinutes()).toString()
  }

  value?.let { inputValue ->
    val validationResult =
      syncIntervalValidation.validateSyncInterval(inputValue)
    val errorString = getValidationErrorString(validationResult)
    val isError = errorString != null

    val onConfirmClick = suspend {
      dataStore.edit {
        it[Settings.KEY_SYNC_INTERVAL] = inputValue.toLong()
      }
      onDismissRequest()
    }

    AlertDialog(
      onDismissRequest = onDismissRequest,
      title = {
        Text(text = stringResource(id = R.string.sync_interval_input_dialog_title))
      },
      text = {
        Column {
          OutlinedTextField(
            value = inputValue,
            onValueChange = {
              value = it
            },
            label = {
              Text(text = stringResource(R.string.sync_interval_input_dialog_hint_interval_amount))
            },
            isError = isError,
            singleLine = true,
            supportingText = {
              if (isError) {
                Text(
                  text = errorString!!,
                  color = MaterialTheme.colorScheme.error,
                  style = MaterialTheme.typography.bodySmall,
                  modifier = Modifier.fillMaxWidth(),
                )
              }
            },
            modifier = Modifier
              .fillMaxWidth()
              .semantics {
                if (isError) {
                  error(errorString!!)
                }
              })
          Spacer(modifier = Modifier.height(8.dp))
        }
      },
      confirmButton = {
        TextButton(
          onClick = {
            scope.launch {
              onConfirmClick()
            }
          },
          enabled = !isError
        ) {
          Text(stringResource(id = R.string.save))
        }
      },
      dismissButton = {
        TextButton(
          onClick = onDismissRequest,
        ) {
          Text(stringResource(id = android.R.string.cancel))
        }
      })
  }
}

@Composable
@Preview
fun SyncIntervalInputDialogPreview() {
  Mdc3Theme {
    SyncIntervalInputDialog({})
  }
}