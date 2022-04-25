package dev.aungkyawpaing.ccdroidx.feature.notification

import dev.aungkyawpaing.ccdroidx.data.BuildStatus
import dev.aungkyawpaing.ccdroidx.data.Project
import javax.inject.Inject

class NotifyProjectStatus @Inject constructor(
  private val notificationManager: NotificationManager
) {

  fun notify(previous: Project, now: Project) {
    if ((previous.lastBuildStatus == BuildStatus.SUCCESS &&
          now.lastBuildStatus != BuildStatus.SUCCESS) ||
      (now.lastBuildTime > previous.lastBuildTime &&
          now.lastBuildStatus != BuildStatus.SUCCESS)
    ) {
      notificationManager.notifyProjectFail(now.name, now.webUrl)
    } else if (previous.lastBuildStatus == BuildStatus.FAILURE && now.lastBuildStatus == BuildStatus.SUCCESS) {
      notificationManager.notifyProjectSuccessAfterFail(now.name, now.webUrl)
    }
  }
}