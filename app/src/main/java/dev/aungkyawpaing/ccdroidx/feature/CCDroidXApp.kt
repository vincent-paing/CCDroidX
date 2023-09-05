package dev.aungkyawpaing.ccdroidx.feature

import androidx.compose.runtime.Composable
import com.google.accompanist.themeadapter.material3.Mdc3Theme
import com.ramcosta.composedestinations.DestinationsNavHost

@Composable
fun CCDroidXApp() {
  Mdc3Theme {
    DestinationsNavHost(navGraph = NavGraphs.root)
  }
}