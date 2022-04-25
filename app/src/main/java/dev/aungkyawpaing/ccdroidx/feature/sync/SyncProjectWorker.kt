package dev.aungkyawpaing.ccdroidx.feature.sync

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dev.aungkyawpaing.ccdroidx.api.NetworkException
import dev.aungkyawpaing.ccdroidx.feature.notification.NotificationManager
import dev.aungkyawpaing.ccdroidx.feature.notification.NotifyProjectStatus
import timber.log.Timber

@HiltWorker
class SyncProjectWorker @AssistedInject constructor(
  @Assisted appContext: Context,
  @Assisted workerParams: WorkerParameters,
  val syncProjects: SyncProjects,
  val notifyProjectStatus: NotifyProjectStatus
) : CoroutineWorker(appContext, workerParams) {

  companion object {
    const val TAG = "SyncProjectWorker"
  }

  override suspend fun doWork(): Result {

    try {
      syncProjects.sync(
        onProjectSynced = notifyProjectStatus::notify
      )
    } catch (exception: NetworkException) {
      Timber.i("failure syncing")
      return Result.failure()
    }

    Timber.i("finished syncing")
    return Result.success()
  }

}
