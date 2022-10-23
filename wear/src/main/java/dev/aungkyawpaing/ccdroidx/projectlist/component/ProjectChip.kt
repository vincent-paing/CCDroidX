package dev.aungkyawpaing.ccdroidx.projectlist.component

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import androidx.wear.compose.material.*
import androidx.wear.remote.interactions.RemoteActivityHelper
import androidx.wear.widget.ConfirmationOverlay
import dev.aungkyawpaing.ccdroidx.R
import dev.aungkyawpaing.ccdroidx.common.extensions.findActivity
import dev.aungkyawpaing.wear.datalayer.MiniProject
import kotlinx.coroutines.guava.await
import kotlinx.coroutines.launch
import timber.log.Timber

private val buildFailColor = Color(0xFFB3261E)
private val buildSuccessColor = Color(0xFF4BB543)

@Composable
fun ProjectChip(
  project: MiniProject
) {
  val context = LocalContext.current
  val coroutineScope = rememberCoroutineScope()
  val remoteActivityHelper = RemoteActivityHelper(context)
  val errorText = stringResource(id = R.string.error_open_app_on_phone)
  val continueOnPhoneText = stringResource(id = R.string.continue_on_phone)

  val openInPhone: () -> Unit = {
    val intent = Intent(Intent.ACTION_VIEW)
      .addCategory(Intent.CATEGORY_BROWSABLE)
      .setData("ccdroidx://project/${project.id}".toUri())

    coroutineScope.launch {
      try {
        remoteActivityHelper.startRemoteActivity(intent).await()
        ConfirmationOverlay()
          .setType(ConfirmationOverlay.OPEN_ON_PHONE_ANIMATION)
          .setMessage(continueOnPhoneText)
          .showOn(context.findActivity()!!)
      } catch (exception: Exception) {
        Timber.e(exception)
        ConfirmationOverlay()
          .setType(ConfirmationOverlay.FAILURE_ANIMATION)
          .setMessage(errorText)
          .showOn(context.findActivity()!!)
      }
    }
  }

  Chip(
    modifier = Modifier.fillMaxWidth(),
    label = {
      Text(text = project.name)
    },
    icon = {
      if (project.isSuccess) {
        Icon(Icons.Filled.Done, contentDescription = null)
      } else {
        Icon(Icons.Filled.Close, contentDescription = null)
      }
    },
    colors = ChipDefaults.primaryChipColors(
      backgroundColor = if (project.isSuccess) buildSuccessColor else buildFailColor
    ),
    onClick = openInPhone
  )
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun ProjectChipSuccessPreview() {
  ProjectChip(project = MiniProject(0, "project", true))
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun ProjectChipFailurePreview() {
  ProjectChip(project = MiniProject(0, "project with a very very very long long name", false))
}