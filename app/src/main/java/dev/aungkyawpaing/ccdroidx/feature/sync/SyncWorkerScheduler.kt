package dev.aungkyawpaing.ccdroidx.feature.sync

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.time.Duration

class SyncWorkerScheduler(
  val context: Context
) {

  fun removeExistingAndScheduleWorker(syncInterval: Duration) {
    schedulePeriodicWork(syncInterval)
  }

  private fun schedulePeriodicWork(duration: Duration) {
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

  fun scheduleOneTimeWork() {
    val constraints = Constraints.Builder()
      .setRequiredNetworkType(NetworkType.CONNECTED)
      .build()

    val syncWorkRequest =
      OneTimeWorkRequestBuilder<SyncProjectWorker>()
        .addTag(SyncProjectWorker.TAG)
        .setConstraints(constraints)
        .build()

    WorkManager.getInstance(context)
      .enqueue(syncWorkRequest)
  }
}
