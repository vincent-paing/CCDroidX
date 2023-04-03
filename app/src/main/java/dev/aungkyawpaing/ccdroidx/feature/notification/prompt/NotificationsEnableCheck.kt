package dev.aungkyawpaing.ccdroidx.feature.notification.prompt

import android.content.Context
import androidx.core.app.NotificationManagerCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NotificationsEnableCheck @Inject constructor(
  @ApplicationContext private val context: Context
) {

  fun areNotificationsEnabled(): Boolean {
    return NotificationManagerCompat.from(context).areNotificationsEnabled()
  }
}