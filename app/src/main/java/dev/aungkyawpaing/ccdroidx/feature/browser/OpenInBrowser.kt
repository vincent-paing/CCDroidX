package dev.aungkyawpaing.ccdroidx.feature.browser

import android.app.Activity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import javax.inject.Inject

class OpenInBrowser {

  fun openInBrowser(activity: Activity, url: String) {
    val customTabsIntent = CustomTabsIntent.Builder().build();
    customTabsIntent.launchUrl(activity, url.toUri())
  }


}