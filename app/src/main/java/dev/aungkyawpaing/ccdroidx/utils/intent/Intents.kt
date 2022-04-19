package dev.aungkyawpaing.ccdroidx.utils.intent

import android.content.Intent
import android.net.Uri
import androidx.core.net.toUri

object Intents {

  fun viewUrl(url: String): Intent {
    return Intent(Intent.ACTION_VIEW, url.toUri())
  }

  fun shareUrl(url: String): Intent {
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.type = "text/plain"
    shareIntent.putExtra(Intent.EXTRA_TEXT, url)
    return shareIntent
  }

}