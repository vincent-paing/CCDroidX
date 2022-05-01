package dev.aungkyawpaing.ccdroidx.feature.sync

import android.content.Context
import androidx.work.*
import dev.aungkyawpaing.ccdroidx.feature.settings.syncinterval.SyncInterval
import java.time.Duration

class SyncWorkerScheduler constructor(
  val context: Context
) {

  fun removeExistingAndScheduleWorker(syncInterval: SyncInterval) {
    removeExistingWorkers()
    scheduleWorker(syncInterval.asDuration())
  }

  private fun removeExistingWorkers() {
    WorkManager.getInstance(context)
      .cancelAllWorkByTag(SyncProjectWorker.TAG)
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
        ExistingPeriodicWorkPolicy.KEEP,
        syncWorkRequest
      )

  }
}