package dev.aungkyawpaing.ccdroidx.feature

import androidx.compose.runtime.Composable
import com.google.android.material.composethemeadapter3.Mdc3Theme

@Composable
fun CCDroidXApp(
  content: @Composable () -> Unit
) {
  Mdc3Theme(
    content = content
  )
}