package dev.aungkyawpaing.ccdroidx.feature.notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationChannelGroupCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.aungkyawpaing.ccdroidx.R
import dev.aungkyawpaing.ccdroidx.feature.MainActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationManager @Inject constructor(
  @ApplicationContext private val context: Context
) {

  companion object {
    private const val CHANNEL_GROUP_ID_BUILD_ALERT = "channel_group_build_alert"
    private const val CHANNEL_ID_BUILD_FAIL = "channel_id_build_fail"
    private const val CHANNEL_ID_BUILD_SUCCESS_AFTER_FAIL = "channel_id_build_success_after_fail"
  }

  val notificationManagerCompat = NotificationManagerCompat.from(context)

  init {
    val notificationChannelGroup =
      NotificationChannelGroupCompat.Builder(CHANNEL_GROUP_ID_BUILD_ALERT)
        .setName(context.getString(R.string.channel_group_build_alert_name))
        .setDescription(context.getString(R.string.channel_group_build_alert_desc))
        .build()
    notificationManagerCompat.createNotificationChannelGroup(notificationChannelGroup)
  }

  fun notifyProjectFail(projectName: String, url: String) {
    val name = context.getString(R.string.channel_build_alert_fail_name)
    val descriptionText = context.getString(R.string.channel_build_alert_fail_desc)
    val importance = NotificationManagerCompat.IMPORTANCE_DEFAULT
    val channel = NotificationChannelCompat.Builder(CHANNEL_ID_BUILD_FAIL, importance)
      .setName(name)
      .setDescription(descriptionText)
      .setGroup(CHANNEL_GROUP_ID_BUILD_ALERT)
      .build()

    notificationManagerCompat.createNotificationChannel(channel)

    val intent = Intent(context, MainActivity::class.java)
    intent.putExtra(MainActivity.INTENT_EXTRA_URL, url)
    val notifyPendingIntent = PendingIntent.getActivity(
      context, 0, intent,
      PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val notification = NotificationCompat.Builder(context, CHANNEL_ID_BUILD_FAIL)
      .setContentTitle(context.getString(R.string.notification_fail_title, projectName))
      .setContentText(context.getString(R.string.notification_fail_content))
      .setSmallIcon(R.drawable.ic_notification)
      .setColor(context.getColor(R.color.build_fail))
      .setAutoCancel(true)
      .setContentIntent(notifyPendingIntent)
      .build()

    notificationManagerCompat.notify(projectName, 0, notification)
  }

  fun notifyProjectSuccessAfterFail(projectName: String, url: String) {
    val name = context.getString(R.string.channel_build_alert_success_after_fail_name)
    val descriptionText = context.getString(R.string.channel_build_alert_success_after_fail_desc)
    val importance = NotificationManagerCompat.IMPORTANCE_DEFAULT
    val channel = NotificationChannelCompat.Builder(CHANNEL_ID_BUILD_SUCCESS_AFTER_FAIL, importance)
      .setName(name)
      .setDescription(descriptionText)
      .setGroup(CHANNEL_GROUP_ID_BUILD_ALERT)
      .build()

    notificationManagerCompat.createNotificationChannel(channel)

    val intent = Intent(context, MainActivity::class.java)
    intent.putExtra(MainActivity.INTENT_EXTRA_URL, url)
    val notifyPendingIntent = PendingIntent.getActivity(
      context, 0, intent,
      PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val notification = NotificationCompat.Builder(context, CHANNEL_ID_BUILD_SUCCESS_AFTER_FAIL)
      .setContentTitle(
        context.getString(
          R.string.notification_success_after_fail_title,
          projectName
        )
      )
      .setContentText(context.getString(R.string.notification_success_after_fail_content))
      .setSmallIcon(R.drawable.ic_notification)
      .setColor(context.getColor(R.color.build_success))
      .setContentIntent(notifyPendingIntent)
      .setAutoCancel(true)
      .build()

    notificationManagerCompat.notify(projectName, 0, notification)
  }
}