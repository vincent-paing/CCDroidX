package dev.aungkyawpaing.ccdroidx.appLaunch.components

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Shop
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
import kotlinx.coroutines.guava.await
import kotlinx.coroutines.launch

fun Context.findActivity(): AppCompatActivity? = when (this) {
  is AppCompatActivity -> this
  is ContextWrapper -> baseContext.findActivity()
  else -> null
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun OpenPlayStoreOnPhoneButton() {

  val context = LocalContext.current
  val coroutineScope = rememberCoroutineScope()
  val marketLink = stringResource(id = R.string.market_link_phone_app)
  val errorText = stringResource(id = R.string.error_open_play_store_on_phone)
  val continueOnPhoneText = stringResource(id = R.string.continue_on_phone)
  val remoteActivityHelper = RemoteActivityHelper(context)

  val openAppInStoreOnPhone: () -> Unit = {
    val intent = Intent(Intent.ACTION_VIEW)
      .addCategory(Intent.CATEGORY_BROWSABLE)
      .setData(marketLink.toUri())

    coroutineScope.launch {
      try {
        remoteActivityHelper.startRemoteActivity(intent).await()
        ConfirmationOverlay()
          .setType(ConfirmationOverlay.OPEN_ON_PHONE_ANIMATION)
          .setMessage(continueOnPhoneText)
          .showOn(context.findActivity()!!)
      } catch (exception: Exception) {
        ConfirmationOverlay()
          .setType(ConfirmationOverlay.FAILURE_ANIMATION)
          .setMessage(errorText)
          .showOn(context.findActivity()!!)
      }
    }
  }

  Button(
    onClick = openAppInStoreOnPhone
  ) {
    Icon(
      Icons.Outlined.Shop,
      contentDescription = stringResource(id = R.string.content_description_open_play_store)
    )
  }
}