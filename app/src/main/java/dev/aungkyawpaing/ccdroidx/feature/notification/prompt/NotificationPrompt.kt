package dev.aungkyawpaing.ccdroidx.feature.notification.prompt

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.material.composethemeadapter3.Mdc3Theme

@Composable
fun NotificationPrompt(
  viewModel: NotificationPromptViewModel = viewModel(),
  modifier: Modifier = Modifier
) {

  val showPrompt = viewModel.promptIsVisibleLiveData.observeAsState(initial = false)

  Box(modifier = modifier) {
    if (showPrompt.value) {
      NotificationPromptCard(onDismissPrompt = {
        viewModel.onDismissClick()
      }, onEnableNotification = {})
    }
  }
}

@Preview
@Composable
fun NotificationPromptPreview() {
  Mdc3Theme {
    NotificationPrompt()
  }
}