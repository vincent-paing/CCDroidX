package dev.aungkyawpaing.ccdroidx._testhelper_

import dev.aungkyawpaing.ccdroidx.data.BuildState
import dev.aungkyawpaing.ccdroidx.data.BuildStatus
import dev.aungkyawpaing.ccdroidx.data.Project
import java.time.Clock
import java.time.ZoneId
import java.time.ZonedDateTime

object ProjectBuilder {

  fun buildProject(
    clock: Clock = Clock.systemDefaultZone()
  ): Project {
    return Project(
      name = "Project Name",
      activity = BuildState.SLEEPING,
      lastBuildStatus = BuildStatus.SUCCESS,
      lastBuildLabel = null,
      lastBuildTime = ZonedDateTime.of(2022, 4, 21, 0, 0, 0, 0, ZoneId.of("UTC")),
      nextBuildTime = null,
      webUrl = "https://example.com/master",
      feedUrl = "https://www.example.com/cc.xml",
      lastSyncedTime = ZonedDateTime.now(clock)
    )
  }
}