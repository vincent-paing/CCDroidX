package dev.aungkyawpaing.ccdroidx.data

import dev.aungkyawpaing.ccdroidx.CCDroidXDb
import dev.aungkyawpaing.ccdroidx.ProjectTable
import javax.inject.Inject

class ProjectRepo @Inject constructor(
  private val db: CCDroidXDb
) {

  fun insertProject(project: Project) {
    db.projectTableQueries.insertOrReplace(
      ProjectTable(
        name = project.name,
        activity = project.activity,
        lastBuildStatus = project.lastBuildStatus,
        lastBuildLabel = project.lastBuildLabel,
        lastBuildTime = project.lastBuildTime,
        nextBuildTime = project.nextBuildTime,
        webUrl = project.webUrl,
        feedUrl = ""
      )
    )
  }

}