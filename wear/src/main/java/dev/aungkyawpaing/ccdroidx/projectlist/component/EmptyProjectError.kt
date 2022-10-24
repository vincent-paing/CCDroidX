package dev.aungkyawpaing.ccdroidx.projectlist.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Text
import dev.aungkyawpaing.ccdroidx.R
import dev.aungkyawpaing.ccdroidx.appLaunch.components.OpenAppOnPhoneButton

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun EmptyProjectError() {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(
        rememberScrollState()
      )
      .padding(24.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Text(
      text = stringResource(id = R.string.empty_projects),
      textAlign = TextAlign.Center,
      modifier = Modifier.fillMaxWidth()
    )
    OpenAppOnPhoneButton()
  }
}