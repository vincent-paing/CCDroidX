package dev.aungkyawpaing.ccdroidx.data._testhelper_

import dev.aungkyawpaing.ccdroidx.common.BuildState
import dev.aungkyawpaing.ccdroidx.common.BuildStatus
import dev.aungkyawpaing.ccdroidx.data.db.ProjectTable
import dev.aungkyawpaing.ccdroidx.data.db.ProjectTableDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.ZonedDateTime

class FakeProjectTableDao : ProjectTableDao {

  private val projectTables = mutableMapOf<Long, ProjectTable>()
  private var idCounter = 0L

  override suspend fun insert(
    name: String,
    activity: BuildState,
    lastBuildStatus: BuildStatus,
    lastBuildLabel: String?,
    lastBuildTime: ZonedDateTime,
    nextBuildTime: ZonedDateTime?,
    webUrl: String,
    feedUrl: String,
    username: String?,
    password: String?
  ) {
    idCounter += 1
    projectTables[idCounter] = ProjectTable(
      id = idCounter,
      name = name,
      activity = activity,
      lastBuildStatus = lastBuildStatus,
      lastBuildLabel = lastBuildLabel,
      lastBuildTime = lastBuildTime,
      nextBuildTime = nextBuildTime,
      webUrl = webUrl,
      feedUrl = feedUrl,
      username = username,
      password = password
    )
  }

  override suspend fun update(
    name: String,
    activity: BuildState,
    lastBuildStatus: BuildStatus,
    lastBuildLabel: String?,
    lastBuildTime: ZonedDateTime,
    nextBuildTime: ZonedDateTime?,
    webUrl: String,
    feedUrl: String,
    username: String?,
    password: String?,
    id: Long
  ) {
    projectTables[idCounter] = projectTables[idCounter]!!.copy(
      name = name,
      activity = activity,
      lastBuildStatus = lastBuildStatus,
      lastBuildLabel = lastBuildLabel,
      lastBuildTime = lastBuildTime,
      nextBuildTime = nextBuildTime,
      webUrl = webUrl,
      feedUrl = feedUrl,
      username = username,
      password = password
    )
  }

  override suspend fun delete(id: Long) {
    projectTables.remove(id)
  }

  override suspend fun updateMute(isMuted: Boolean, mutedUntil: ZonedDateTime?, id: Long) {
    projectTables[idCounter] = projectTables[idCounter]!!.copy(
      isMuted = isMuted,
      mutedUntil = mutedUntil
    )
  }

  override fun selectAll(): Flow<List<ProjectTable>> {
    return flowOf(projectTables.values.toList())
  }

  override fun selectByNotBuildStatus(lastBuildStatus: BuildStatus): Flow<List<ProjectTable>> {
    return flowOf(projectTables.values.filter { it.lastBuildStatus != lastBuildStatus })
  }

  override fun selectById(id: Long): ProjectTable {
    return projectTables[idCounter]!!
  }
}