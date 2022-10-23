package dev.aungkyawpaing.ccdroidx.data

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import dev.aungkyawpaing.ccdroidx.CCDroidXDb
import dev.aungkyawpaing.ccdroidx.common.Authentication
import dev.aungkyawpaing.ccdroidx.common.Project
import dev.aungkyawpaing.ccdroidx.common.coroutine.DispatcherProvider
import dev.aungkyawpaing.ccdroidx.data.api.FetchProject
import dev.aungkyawpaing.ccdroidx.data.db.ProjectTableToProjectMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProjectRepo @Inject constructor(
  private val fetchProject: FetchProject,
  private val db: CCDroidXDb,
  private val cryptography: Cryptography,
  private val projectTableToProjectMapper: ProjectTableToProjectMapper,
  private val dispatcherProvider: DispatcherProvider
) {

  suspend fun fetchRepo(
    url: String,
    username: String? = null,
    password: String? = null
  ): List<Project> {
    return withContext(dispatcherProvider.io()) {
      fetchProject.requestForProjectList(url, username, password).map { response ->
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
          mutedUntil = null,
          authentication = if (username != null && password != null) {
            Authentication(username, password)
          } else null
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
          username = project.authentication?.username,
          password = if (project.authentication != null) cryptography.encrypt(project.authentication!!.password) else null
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
          username = project.authentication?.username,
          password = if (project.authentication != null) cryptography.encrypt(project.authentication!!.password) else null
        )
      }
    }
  }

  fun getAll(): Flow<List<Project>> {
    return db.projectTableQueries.selectAll()
      .asFlow()
      .mapToList(dispatcherProvider.default())
      .map { projectTables ->
        projectTables.map(projectTableToProjectMapper::mapProjectTable)
      }
  }


  fun getById(projectId: Long): Flow<Project> {
    return db.projectTableQueries.selectById(projectId)
      .asFlow()
      .mapToOne(dispatcherProvider.default())
      .map(projectTableToProjectMapper::mapProjectTable)
  }

  suspend fun delete(projectId: Long) {
    return withContext(dispatcherProvider.io()) {
      db.projectTableQueries.delete(projectId)
    }
  }

  suspend fun unmuteProject(projectId: Long) {
    withContext(dispatcherProvider.io()) {
      db.projectTableQueries.updateMute(false, null, projectId)
    }
  }

  suspend fun muteProject(projectId: Long) {
    withContext(dispatcherProvider.io()) {
      db.projectTableQueries.updateMute(true, null, projectId)
    }
  }

}