package dev.aungkyawpaing.ccdroidx.feature.notification

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.material.composethemeadapter3.Mdc3Theme
import dev.aungkyawpaing.ccdroidx.BuildConfig

@Composable
fun NotificationPrompt(modifier: Modifier = Modifier) {

  Box(modifier = modifier) {
    if (BuildConfig.DEBUG) {
      NotificationPromptCard()
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