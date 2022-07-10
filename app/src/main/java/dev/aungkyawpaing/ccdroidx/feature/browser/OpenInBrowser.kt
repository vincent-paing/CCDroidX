package dev.aungkyawpaing.ccdroidx.feature.browser

import android.app.Activity
import android.content.ActivityNotFoundException
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import androidx.preference.PreferenceManager
import dev.aungkyawpaing.ccdroidx.R
import dev.aungkyawpaing.ccdroidx.utils.extensions.showShortToast
import dev.aungkyawpaing.ccdroidx.utils.intent.Intents

fun openInBrowser(activity: Activity, url: String) {
  val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
  val doesLinkOpenExternally = sharedPreferences.getBoolean("others_browser", false)


  if (doesLinkOpenExternally) {
    try {
      val viewUrlIntent = Intents.viewUrl(url)
      activity.startActivity(viewUrlIntent)
    } catch (exception: ActivityNotFoundException) {
      activity.showShortToast(activity.getString(R.string.error_no_browser_installed))
    }
  } else {
    val customTabsIntent = CustomTabsIntent.Builder().build()
    customTabsIntent.launchUrl(activity, url.toUri())
  }
}
