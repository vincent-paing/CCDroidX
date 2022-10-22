package dev.aungkyawpaing.ccdroidx.theme

import androidx.compose.runtime.Composable
import androidx.wear.compose.material.MaterialTheme

@Composable
fun CCDroidXWearTheme(
  content: @Composable () -> Unit
) {
  MaterialTheme(
    colors = wearColorPalette,
    content = content
  )
}