package dev.aungkyawpaing.ccdroidx.feature.sync

import dev.aungkyawpaing.ccdroidx.api.NetworkException
import dev.aungkyawpaing.ccdroidx.data.Project
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

  suspend fun sync(
    onProjectSynced: ((@ParameterName("previous") Project, @ParameterName("now") Project) -> Unit)
  ) {
    syncMetaDataStorage.saveLastSyncedTime(
      LastSyncedStatus(
        lastSyncedDateTime = ZonedDateTime.now(clock),
        lastSyncedState = LastSyncedState.SYNCING
      )
    )
    try {
      (projectRepo.getAll().firstOrNull() ?: emptyList()).forEach { project ->
        val updatedProject = projectRepo.fetchRepo(
          project.feedUrl,
          project.authentication?.username,
          project.authentication?.password
        ).find {
          it.name == project.name
        }?.copy(id = project.id) ?: return@forEach

        onProjectSynced(project, updatedProject)
        projectRepo.saveProject(updatedProject)
      }
      syncMetaDataStorage.saveLastSyncedTime(
        LastSyncedStatus(
          lastSyncedDateTime = ZonedDateTime.now(clock),
          lastSyncedState = LastSyncedState.SUCCESS
        )
      )
    } catch (networkException: NetworkException) {
      syncMetaDataStorage.saveLastSyncedTime(
        LastSyncedStatus(
          lastSyncedDateTime = ZonedDateTime.now(clock),
          lastSyncedState = LastSyncedState.FAILED,
          errorCode = 1
        )
      )
      throw networkException
    }

  }
}