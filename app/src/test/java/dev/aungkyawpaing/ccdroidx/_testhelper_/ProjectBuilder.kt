package dev.aungkyawpaing.ccdroidx._testhelper_

import dev.aungkyawpaing.ccdroidx.data.BuildState
import dev.aungkyawpaing.ccdroidx.data.BuildStatus
import dev.aungkyawpaing.ccdroidx.data.Project
import java.time.Clock
import java.time.ZoneId
import java.time.ZonedDateTime

object ProjectBuilder {

  fun buildProject(
    id: Long = 1,
    name: String = "Project Name",
    lastBuildStatus: BuildStatus = BuildStatus.SUCCESS,
    webUrl: String = "https://example.com/master",
    lastBuildTime: ZonedDateTime = ZonedDateTime.of(2022, 4, 21, 0, 0, 0, 0, ZoneId.of("UTC"))
  ): Project {
    return Project(
      id = id,
      name = name,
      activity = BuildState.SLEEPING,
      lastBuildStatus = lastBuildStatus,
      lastBuildLabel = null,
      lastBuildTime = lastBuildTime,
      nextBuildTime = null,
      webUrl = webUrl,
      feedUrl = "https://www.example.com/cc.xml"
    )
  }
}