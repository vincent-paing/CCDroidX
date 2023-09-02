package dev.aungkyawpaing.ccdroidx.feature.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import dev.aungkyawpaing.ccdroidx.R
import dev.aungkyawpaing.ccdroidx.feature.add.component.FeedUrlTextField
import dev.aungkyawpaing.ccdroidx.feature.add.component.PasswordTextField
import dev.aungkyawpaing.ccdroidx.feature.add.component.SelectProjectDialog
import dev.aungkyawpaing.ccdroidx.feature.add.component.UsernameTextField
import dev.aungkyawpaing.ccdroidx.feature.add.feedurlvalidation.FeedUrlValidationResult
import dev.aungkyawpaing.ccdroidx.feature.add.passwordvalidation.PasswordValidationResult
import dev.aungkyawpaing.ccdroidx.feature.add.usernamevalidation.UsernameValidationResult

@Composable
fun AddProjectDialog(
  viewModel: AddProjectViewModel = hiltViewModel(),
  onDismissRequest: () -> Unit
) {

  var feedUrl by rememberSaveable { mutableStateOf("") }
  val feedUrlValidation =
    viewModel.feedUrlValidationResult.observeAsState(initial = FeedUrlValidationResult.CORRECT)

  var requireAuth by rememberSaveable { mutableStateOf(false) }

  var username by rememberSaveable { mutableStateOf("") }
  val usernameValidation =
    viewModel.usernameValidationResult.observeAsState(initial = UsernameValidationResult.CORRECT)

  var password by rememberSaveable { mutableStateOf("") }
  val passwordValidation =
    viewModel.passwordValidationResult.observeAsState(initial = PasswordValidationResult.CORRECT)

  val isLoading = viewModel.isLoadingLiveData.observeAsState(initial = false)

  val showProjectEvent = viewModel.showProjectListLiveEvent.observeAsState()

  AlertDialog(
    onDismissRequest = onDismissRequest,
    title = {
      Column {
        Text(text = stringResource(id = R.string.add_new_project))

        if (isLoading.value) {
          LinearProgressIndicator(
            modifier = Modifier
              .fillMaxWidth()
              .padding(top = 8.dp)
          )
        }

      }
    },
    properties = DialogProperties(
      dismissOnClickOutside = !isLoading.value,
      dismissOnBackPress = !isLoading.value
    ),
    text = {
      Column {
        FeedUrlTextField(
          value = feedUrl,
          onValueChange = {
            feedUrl = it
          },
          isEnabled = !isLoading.value,
          feedUrlValidation = feedUrlValidation.value
        )

        val onRequireAuthCheckChange: (Boolean) -> Unit = { isChecked ->
          requireAuth = isChecked
        }

        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .semantics(true) {
              stateDescription = if (requireAuth) {
                "Checked Check Box Require Authentication"
              } else {
                "Not checked Check Box Require Authentication"
              }

              onClick {
                onRequireAuthCheckChange(!requireAuth)
                true
              }
            },
          horizontalArrangement = Arrangement.Center,
        ) {
          Checkbox(
            checked = requireAuth, onCheckedChange = onRequireAuthCheckChange,
            enabled = !isLoading.value,
            modifier = Modifier
              .align(Alignment.CenterVertically)
              .clearAndSetSemantics { }
          )
          Text(
            text = stringResource(R.string.require_auth),
            modifier = Modifier
              .align(Alignment.CenterVertically)
              .clearAndSetSemantics { }
          )
        }

        UsernameTextField(
          value = username,
          onValueChange = { username = it },
          isEnabled = requireAuth && !isLoading.value,
          usernameValidation = usernameValidation.value
        )

        PasswordTextField(
          value = password,
          onValueChange = {
            password = it
          },
          isEnabled = requireAuth && !isLoading.value,
          passwordValidationResult = passwordValidation.value
        )
      }

    },
    confirmButton = {
      TextButton(
        onClick = {
          viewModel.onClickNext(
            feedUrl = feedUrl,
            requireAuth = requireAuth,
            username = username,
            password = password
          )
        },
        enabled = !isLoading.value
      ) {
        Text(stringResource(id = R.string.next))
      }
    },
    dismissButton = {
      TextButton(
        onClick = onDismissRequest,
        enabled = !isLoading.value
      ) {
        Text(stringResource(id = android.R.string.cancel))
      }
    }
  )

  val projectList = showProjectEvent.value
  if (projectList != null) {
    SelectProjectDialog(
      projectList,
      onProjectSelect = {
        viewModel.onSelectProject(it)
        viewModel.onDismissSelectProject()
        onDismissRequest()
      },
      onDismissRequest = {
        viewModel.onDismissSelectProject()
      }
    )
  }
}
