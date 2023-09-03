package dev.aungkyawpaing.ccdroidx.feature.browser

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import dev.aungkyawpaing.ccdroidx.R
import dev.aungkyawpaing.ccdroidx.feature.settings.Settings
import dev.aungkyawpaing.ccdroidx.feature.settings.settingsDataStore
import kotlinx.coroutines.flow.firstOrNull

suspend fun openInBrowser(activity: Activity, url: String) {
  val doesLinkOpenExternally =
    activity.settingsDataStore.data.firstOrNull()?.get(Settings.KEY_OPEN_EXTERNAL_BROWSER)
      ?: Settings.DEFAULT_OPEN_EXTERNAL_BROWSER

  if (doesLinkOpenExternally) {
    try {
      val viewUrlIntent = Intent(Intent.ACTION_VIEW, url.toUri())
      activity.startActivity(viewUrlIntent)
    } catch (exception: ActivityNotFoundException) {
      Toast.makeText(activity, R.string.error_no_browser_installed, Toast.LENGTH_SHORT).show()
    }
  } else {
    val customTabsIntent = CustomTabsIntent.Builder().build()
    customTabsIntent.launchUrl(activity, url.toUri())
  }
}
