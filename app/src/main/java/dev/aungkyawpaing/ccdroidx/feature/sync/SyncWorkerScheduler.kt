package dev.aungkyawpaing.ccdroidx.feature.sync

import android.content.Context
import androidx.work.*
import java.time.Duration

class SyncWorkerScheduler(
  val context: Context
) {

  fun removeExistingAndScheduleWorker(syncInterval: Duration) {
    scheduleWorker(syncInterval)
  }

  private fun scheduleWorker(duration: Duration) {
    val constraints = Constraints.Builder()
      .setRequiredNetworkType(NetworkType.CONNECTED)
      .setRequiresBatteryNotLow(true)
      .build()

    val syncWorkRequest =
      PeriodicWorkRequestBuilder<SyncProjectWorker>(
        duration
      ).addTag(SyncProjectWorker.TAG)
        .setConstraints(constraints)
        .build()

    WorkManager.getInstance(context)
      .enqueueUniquePeriodicWork(
        SyncProjectWorker.TAG,
        ExistingPeriodicWorkPolicy.UPDATE,
        syncWorkRequest
      )

  }
}