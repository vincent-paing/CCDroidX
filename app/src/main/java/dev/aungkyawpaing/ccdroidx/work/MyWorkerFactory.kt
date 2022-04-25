package dev.aungkyawpaing.ccdroidx.work

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import dev.aungkyawpaing.ccdroidx.feature.notification.NotifyProjectStatus
import dev.aungkyawpaing.ccdroidx.feature.sync.SyncProjectWorker
import dev.aungkyawpaing.ccdroidx.feature.sync.SyncProjects
import javax.inject.Inject

class MyWorkerFactory @Inject constructor(
  private val syncProjects: SyncProjects,
  private val notifyProjectStatus: NotifyProjectStatus
) :
  WorkerFactory() {
  override fun createWorker(
    appContext: Context,
    workerClassName: String,
    workerParameters: WorkerParameters
  ): ListenableWorker =
    SyncProjectWorker(appContext, workerParameters, syncProjects, notifyProjectStatus)
}
