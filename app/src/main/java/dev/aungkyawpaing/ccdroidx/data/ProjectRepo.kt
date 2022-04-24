package dev.aungkyawpaing.ccdroidx.data

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import dev.aungkyawpaing.ccdroidx.CCDroidXDb
import dev.aungkyawpaing.ccdroidx.ProjectTable
import dev.aungkyawpaing.ccdroidx.api.FetchProject
import dev.aungkyawpaing.ccdroidx.coroutine.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProjectRepo @Inject constructor(
  private val fetchProject: FetchProject,
  private val db: CCDroidXDb,
  private val dispatcherProvider: DispatcherProvider
) {

  fun fetchRepo(url: String): List<Project> {
    return fetchProject.requestForProjectList(url)
  }


  fun saveProject(project: Project) {
    db.projectTableQueries.insertOrReplace(
      ProjectTable(
        name = project.name,
        activity = project.activity,
        lastBuildStatus = project.lastBuildStatus,
        lastBuildLabel = project.lastBuildLabel,
        lastBuildTime = project.lastBuildTime,
        nextBuildTime = project.nextBuildTime,
        webUrl = project.webUrl,
        feedUrl = project.feedUrl
      )
    )
  }

  fun getAll(): Flow<List<Project>> {
    return db.projectTableQueries.selectAll()
      .asFlow()
      .mapToList(dispatcherProvider.default())
      .map { projectTables ->
        projectTables.map { projectTable ->
          Project(
            name = projectTable.name,
            activity = projectTable.activity,
            lastBuildStatus = projectTable.lastBuildStatus,
            lastBuildLabel = projectTable.lastBuildLabel,
            lastBuildTime = projectTable.lastBuildTime,
            nextBuildTime = projectTable.nextBuildTime,
            webUrl = projectTable.webUrl,
            feedUrl = projectTable.feedUrl
          )
        }
      }
  }

}