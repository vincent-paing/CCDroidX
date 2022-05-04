package dev.aungkyawpaing.ccdroidx.data

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import dev.aungkyawpaing.ccdroidx.CCDroidXDb
import dev.aungkyawpaing.ccdroidx.api.FetchProject
import dev.aungkyawpaing.ccdroidx.coroutine.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProjectRepo @Inject constructor(
  private val fetchProject: FetchProject,
  private val db: CCDroidXDb,
  private val dispatcherProvider: DispatcherProvider
) {

  suspend fun fetchRepo(url: String): List<Project> {
    return withContext(dispatcherProvider.io()) {
      fetchProject.requestForProjectList(url).map { response ->
        Project(
          id = -1L,
          name = response.name,
          activity = response.activity,
          lastBuildStatus = response.lastBuildStatus,
          lastBuildLabel = response.lastBuildLabel,
          lastBuildTime = response.lastBuildTime,
          nextBuildTime = response.nextBuildTime,
          webUrl = response.webUrl,
          feedUrl = response.feedUrl,
          isMuted = false,
          mutedUntil = null
        )
      }
    }
  }


  suspend fun saveProject(project: Project) {
    withContext(dispatcherProvider.io()) {
      if (project.id == -1L) {
        db.projectTableQueries.insert(
          name = project.name,
          activity = project.activity,
          lastBuildStatus = project.lastBuildStatus,
          lastBuildLabel = project.lastBuildLabel,
          lastBuildTime = project.lastBuildTime,
          nextBuildTime = project.nextBuildTime,
          webUrl = project.webUrl,
          feedUrl = project.feedUrl,
        )
      } else {
        db.projectTableQueries.update(
          id = project.id,
          name = project.name,
          activity = project.activity,
          lastBuildStatus = project.lastBuildStatus,
          lastBuildLabel = project.lastBuildLabel,
          lastBuildTime = project.lastBuildTime,
          nextBuildTime = project.nextBuildTime,
          webUrl = project.webUrl,
          feedUrl = project.feedUrl,
        )
      }
    }
  }

  fun getAll(): Flow<List<Project>> {
    return db.projectTableQueries.selectAll()
      .asFlow()
      .mapToList(dispatcherProvider.default())
      .map { projectTables ->
        projectTables.map { projectTable ->
          Project(
            id = projectTable.id,
            name = projectTable.name,
            activity = projectTable.activity,
            lastBuildStatus = projectTable.lastBuildStatus,
            lastBuildLabel = projectTable.lastBuildLabel,
            lastBuildTime = projectTable.lastBuildTime,
            nextBuildTime = projectTable.nextBuildTime,
            webUrl = projectTable.webUrl,
            feedUrl = projectTable.feedUrl,
            isMuted = projectTable.isMuted,
            mutedUntil = projectTable.mutedUntil
          )
        }
      }

  }

  suspend fun delete(project: Project) {
    return withContext(dispatcherProvider.io()) {
      db.projectTableQueries.delete(project.id)
    }
  }

  suspend fun unmuteProject() {
    withContext(dispatcherProvider.io()) {
      db.projectTableQueries.updateMute(false, null)
    }
  }

  suspend fun muteProject() {
    withContext(dispatcherProvider.io()) {
      db.projectTableQueries.updateMute(true, null)
    }
  }

}