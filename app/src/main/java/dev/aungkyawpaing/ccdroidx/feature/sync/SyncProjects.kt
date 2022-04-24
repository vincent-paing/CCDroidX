package dev.aungkyawpaing.ccdroidx.feature.sync

import dev.aungkyawpaing.ccdroidx.data.ProjectRepo
import kotlinx.coroutines.flow.firstOrNull

class SyncProjects(private val projectRepo: ProjectRepo) {

  suspend fun sync() {
    (projectRepo.getAll().firstOrNull() ?: emptyList()).forEach { project ->
      val updatedProject = projectRepo.fetchRepo(project.feedUrl).find {
        it.webUrl == project.webUrl
      } ?: return@forEach

      projectRepo.saveProject(updatedProject)
    }
  }


}