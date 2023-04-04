package dev.aungkyawpaing.ccdroidx.feature.notification.prompt

import android.content.Intent
import android.provider.Settings
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.themeadapter.material3.Mdc3Theme

@Composable
fun NotificationPrompt(
  modifier: Modifier = Modifier,
  viewModel: NotificationPromptViewModel = viewModel()
) {
  val context = LocalContext.current
  val showPrompt = viewModel.promptIsVisibleLiveData.observeAsState(initial = false)

  Box(modifier = modifier) {
    AnimatedVisibility(
      visible = showPrompt.value,
      enter = fadeIn() + slideInVertically(
        initialOffsetY = {
          it / 2
        },
      ),
      exit = fadeOut() + slideOutVertically(
        targetOffsetY = {
          it / 2
        },
      )
    ) {
      NotificationPromptCard(onDismissPrompt = {
        viewModel.onDismissClick()
      }, onEnableNotification = {
        kotlin.runCatching {
          val settingsIntent: Intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            .putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
          context.startActivity(settingsIntent)
        }
      })
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