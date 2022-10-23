package dev.aungkyawpaing.ccdroidx.feature.browser

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import androidx.preference.PreferenceManager
import dev.aungkyawpaing.ccdroidx.R

fun openInBrowser(activity: Activity, url: String) {
  val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
  val doesLinkOpenExternally = sharedPreferences.getBoolean("others_browser", false)

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
