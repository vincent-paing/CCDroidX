package dev.aungkyawpaing.ccdroidx.feature.add

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.aungkyawpaing.ccdroidx.R
import dev.aungkyawpaing.ccdroidx.feature.add.component.FeedUrlTextField
import dev.aungkyawpaing.ccdroidx.feature.add.component.PasswordTextField
import dev.aungkyawpaing.ccdroidx.feature.add.component.SelectProjectDialog
import dev.aungkyawpaing.ccdroidx.feature.add.component.UsernameTextField
import dev.aungkyawpaing.ccdroidx.feature.add.feedurlvalidation.FeedUrlValidationResult
import dev.aungkyawpaing.ccdroidx.feature.add.passwordvalidation.PasswordValidationResult
import dev.aungkyawpaing.ccdroidx.feature.add.usernamevalidation.UsernameValidationResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProjectDialog(
  viewModel: AddProjectViewModel = viewModel(),
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
          isDisabled = !isLoading.value,
          feedUrlValidation = feedUrlValidation.value
        )

        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
          horizontalArrangement = Arrangement.Center
        ) {
          Checkbox(
            checked = requireAuth, onCheckedChange = { requireAuth = it },
            enabled = !isLoading.value,
            modifier = Modifier.align(Alignment.CenterVertically)
          )
          Text(
            text = stringResource(R.string.require_auth),
            modifier = Modifier.align(Alignment.CenterVertically)
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
