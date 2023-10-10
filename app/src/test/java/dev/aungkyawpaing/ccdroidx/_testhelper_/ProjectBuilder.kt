package dev.aungkyawpaing.ccdroidx._testhelper_

import dev.aungkyawpaing.ccdroidx.common.Authentication
import dev.aungkyawpaing.ccdroidx.common.BuildState
import dev.aungkyawpaing.ccdroidx.common.BuildStatus
import dev.aungkyawpaing.ccdroidx.common.Project
import java.time.ZoneId
import java.time.ZonedDateTime

object ProjectBuilder {

  fun buildProject(
    id: Long = 1,
    name: String = "Project Name",
    activity: BuildState = BuildState.SLEEPING,
    lastBuildStatus: BuildStatus = BuildStatus.SUCCESS,
    lastBuildLabel: String? = null,
    webUrl: String = "https://example.com/master",
    lastBuildTime: ZonedDateTime = ZonedDateTime.of(2022, 4, 21, 0, 0, 0, 0, ZoneId.of("UTC"))
  ): Project {
    return Project(
      id = id,
      name = name,
      activity = activity,
      lastBuildStatus = lastBuildStatus,
      lastBuildLabel = lastBuildLabel,
      lastBuildTime = lastBuildTime,
      nextBuildTime = null,
      webUrl = webUrl,
      feedUrl = "https://www.example.com/cc.xml",
      isMuted = false,
      mutedUntil = null,
      authentication = Authentication(
        username = "username",
        password = "password"
      )
    )
  }
}