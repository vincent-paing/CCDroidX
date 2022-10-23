package dev.aungkyawpaing.ccdroidx.data.db

import dev.aungkyawpaing.ccdroidx.ProjectTable
import dev.aungkyawpaing.ccdroidx.common.Authentication
import dev.aungkyawpaing.ccdroidx.common.Project
import dev.aungkyawpaing.ccdroidx.data.Cryptography
import javax.inject.Inject

class ProjectTableToProjectMapper @Inject constructor(
  private val cryptography: Cryptography,
) {

  fun mapProjectTable(projectTable: ProjectTable): Project {
    return Project(
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
      mutedUntil = projectTable.mutedUntil,
      authentication = if (projectTable.username != null && projectTable.password != null) {
        Authentication(
          username = projectTable.username, password = cryptography.decrypt(projectTable.password)
        )
      } else null
    )
  }

}