package dev.aungkyawpaing.ccdroidx.feature.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.themeadapter.material3.Mdc3Theme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import dev.aungkyawpaing.ccdroidx.R
import dev.aungkyawpaing.ccdroidx.common.Project
import dev.aungkyawpaing.ccdroidx.feature.add.component.FeedUrlTextField
import dev.aungkyawpaing.ccdroidx.feature.add.component.PasswordTextField
import dev.aungkyawpaing.ccdroidx.feature.add.component.SelectProjectDialog
import dev.aungkyawpaing.ccdroidx.feature.add.component.UsernameTextField
import dev.aungkyawpaing.ccdroidx.feature.add.feedurlvalidation.FeedUrlValidationResult
import dev.aungkyawpaing.ccdroidx.feature.add.passwordvalidation.PasswordValidationResult
import dev.aungkyawpaing.ccdroidx.feature.add.usernamevalidation.UsernameValidationResult

@Destination(style = DestinationStyle.Dialog::class)
@Composable
fun AddProjectDialogScreen(
  viewModel: AddProjectViewModel = hiltViewModel(),
  navigator: DestinationsNavigator
) {
  AddProjectDialogContent(
    feedUrlValidation = viewModel.feedUrlValidationResult.observeAsState(initial = FeedUrlValidationResult.CORRECT).value,
    usernameValidation = viewModel.usernameValidationResult.observeAsState(initial = UsernameValidationResult.CORRECT).value,
    passwordValidation = viewModel.passwordValidationResult.observeAsState(initial = PasswordValidationResult.CORRECT).value,
    isLoading = viewModel.isLoadingLiveData.observeAsState(initial = false).value,
    showProjectListEvent = viewModel.showProjectListLiveEvent.observeAsState().value,
    onClickNext = viewModel::onClickNext,
    onSelectProject = viewModel::onSelectProject,
    onDismissSelectProject = viewModel::onDismissSelectProject,
    navigator = navigator
  )
}

@Composable
fun AddProjectDialogContent(
  feedUrlValidation: FeedUrlValidationResult,
  usernameValidation: UsernameValidationResult,
  passwordValidation: PasswordValidationResult,
  isLoading: Boolean,
  showProjectListEvent: List<Project>?,
  onClickNext: (feedUrl: String, requireAuth: Boolean, username: String, password: String) -> Unit,
  onSelectProject: (project: Project) -> Unit,
  onDismissSelectProject: () -> Unit,
  navigator: DestinationsNavigator
) {

  var feedUrl by rememberSaveable { mutableStateOf("") }
  var requireAuth by rememberSaveable { mutableStateOf(false) }
  var username by rememberSaveable { mutableStateOf("") }
  var password by rememberSaveable { mutableStateOf("") }

  // Width and padding set according to https://m3.material.io/components/dialogs/specs
  Card(
    shape = RoundedCornerShape(28.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surfaceVariant,
      contentColor = MaterialTheme.colorScheme.onSurfaceVariant
    ),
    modifier = Modifier
      .widthIn(min = 280.dp, max = 560.dp)
      .wrapContentHeight()
      .zIndex(8.0f)
  ) {

    Column(
      modifier = Modifier.padding(24.dp)
    ) {
      Column {
        Text(
          text = stringResource(id = R.string.add_new_project),
          style = MaterialTheme.typography.headlineSmall,
          color = MaterialTheme.colorScheme.onSurface
        )

        if (isLoading) {
          LinearProgressIndicator(
            modifier = Modifier
              .fillMaxWidth()
              .padding(top = 8.dp)
          )
        }
      }

      Column(modifier = Modifier.padding(top = 16.dp, bottom = 24.dp)) {
        FeedUrlTextField(
          value = feedUrl,
          onValueChange = {
            feedUrl = it
          },
          isEnabled = !isLoading,
          feedUrlValidation = feedUrlValidation
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
            enabled = !isLoading,
            modifier = Modifier
              .align(Alignment.CenterVertically)
              .clearAndSetSemantics {
                testTag = "checkbox-require-auth"
              }

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
          isEnabled = requireAuth && !isLoading,
          usernameValidation = usernameValidation
        )

        PasswordTextField(
          value = password,
          onValueChange = {
            password = it
          },
          isEnabled = requireAuth && !isLoading,
          passwordValidationResult = passwordValidation
        )
      }

      Row(modifier = Modifier.align(Alignment.End)) {
        TextButton(
          onClick = {
            navigator.navigateUp()
          },
          enabled = !isLoading
        ) {
          Text(stringResource(id = android.R.string.cancel))
        }
        TextButton(
          onClick = {
            onClickNext(
              feedUrl,
              requireAuth,
              username,
              password
            )
          },
          enabled = !isLoading
        ) {
          Text(stringResource(id = R.string.next))
        }
      }
    }
  }

  if (showProjectListEvent != null) {
    SelectProjectDialog(
      showProjectListEvent,
      onProjectSelect = {
        onSelectProject(it)
        onDismissSelectProject()
        navigator.navigateUp()
      },
      onDismissRequest = {
        onDismissSelectProject()
      }
    )
  }
}

@Preview
@Composable
fun AddProjectDialogContentPreview() {
  Mdc3Theme {
    Surface(
      modifier = Modifier
        .fillMaxSize()
    ) {
      AddProjectDialogContent(
        feedUrlValidation = FeedUrlValidationResult.CORRECT,
        usernameValidation = UsernameValidationResult.CORRECT,
        passwordValidation = PasswordValidationResult.CORRECT,
        isLoading = false,
        showProjectListEvent = null,
        onClickNext = { _, _, _, _ -> },
        onSelectProject = { _ -> },
        onDismissSelectProject = {},
        navigator = EmptyDestinationsNavigator
      )
    }
  }
}
