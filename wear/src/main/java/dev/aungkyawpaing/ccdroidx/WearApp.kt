package dev.aungkyawpaing.ccdroidx

import androidx.compose.runtime.Composable
import dev.aungkyawpaing.ccdroidx.theme.CCDroidXWearTheme

@Composable
fun WearApp(
  content: @Composable () -> Unit
) {
  CCDroidXWearTheme(content = content)
}