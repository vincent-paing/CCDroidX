package dev.aungkyawpaing.ccdroidx

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.TimeText
import dev.aungkyawpaing.ccdroidx.theme.CCDroidXWearTheme

@Composable
fun WearApp(
  content: @Composable () -> Unit
) {

  CCDroidXWearTheme {
    Scaffold(
      modifier = Modifier.background(MaterialTheme.colors.background),
      timeText = {
        TimeText()
      }
    ) {
      content()
    }
  }
}