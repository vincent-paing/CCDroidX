package dev.aungkyawpaing.ccdroidx.feature.sync

import dev.aungkyawpaing.ccdroidx.data.ProjectRepo
import kotlinx.coroutines.flow.firstOrNull
import java.time.Clock
import java.time.ZonedDateTime
import javax.inject.Inject

class SyncProjects @Inject constructor(
  private val projectRepo: ProjectRepo,
  private val syncMetaDataStorage: SyncMetaDataStorage,
  private val clock: Clock
) {

  suspend fun sync() {
    (projectRepo.getAll().firstOrNull() ?: emptyList()).forEach { project ->
      val updatedProject = projectRepo.fetchRepo(project.feedUrl).find {
        it.webUrl == project.webUrl
      } ?: return@forEach

      projectRepo.saveProject(updatedProject)
    }
    syncMetaDataStorage.saveLastSyncedTime(ZonedDateTime.now(clock))
  }
}