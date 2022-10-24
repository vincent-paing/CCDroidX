package dev.aungkyawpaing.ccdroidx.appLaunch.components

import android.content.Intent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SendToMobile
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Icon
import androidx.wear.remote.interactions.RemoteActivityHelper
import androidx.wear.widget.ConfirmationOverlay
import dev.aungkyawpaing.ccdroidx.R
import dev.aungkyawpaing.ccdroidx.common.extensions.findActivity
import kotlinx.coroutines.guava.await
import kotlinx.coroutines.launch
import timber.log.Timber

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun OpenAppOnPhoneButton() {

  val context = LocalContext.current
  val coroutineScope = rememberCoroutineScope()
  val errorText = stringResource(id = R.string.error_open_app_on_phone)
  val continueOnPhoneText = stringResource(id = R.string.continue_on_phone)
  val remoteActivityHelper = RemoteActivityHelper(context)

  val openAppOnPhone: () -> Unit = {
    val intent = Intent(Intent.ACTION_VIEW).addCategory(Intent.CATEGORY_BROWSABLE)
      .setData("ccdroidx://project".toUri())

    coroutineScope.launch {
      try {
        remoteActivityHelper.startRemoteActivity(intent).await()
        ConfirmationOverlay().setType(ConfirmationOverlay.OPEN_ON_PHONE_ANIMATION)
          .setMessage(continueOnPhoneText).showOn(context.findActivity()!!)
      } catch (exception: Exception) {
        Timber.e(exception)
        ConfirmationOverlay().setType(ConfirmationOverlay.FAILURE_ANIMATION).setMessage(errorText)
          .showOn(context.findActivity()!!)
      }
    }
  }

  Button(
    onClick = openAppOnPhone
  ) {
    Icon(
      Icons.Outlined.SendToMobile,
      contentDescription = stringResource(id = R.string.content_description_open_app_on_phone)
    )
  }
}