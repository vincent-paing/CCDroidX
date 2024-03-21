package dev.aungkyawpaing.ccdroidx.data.db

import androidx.room.Dao
import androidx.room.Query
import dev.aungkyawpaing.ccdroidx.common.BuildState
import dev.aungkyawpaing.ccdroidx.common.BuildStatus
import kotlinx.coroutines.flow.Flow
import java.time.ZonedDateTime

@Dao
interface ProjectTableDao {

  @Query(
    "INSERT INTO ProjectTable(name, activity, lastBuildStatus, lastBuildLabel, lastBuildTime, nextBuildTime, webUrl, feedUrl, username, password) " +
        "VALUES (:name, :activity, :lastBuildStatus, :lastBuildLabel, :lastBuildTime, :nextBuildTime, :webUrl, :feedUrl, :username, :password);"
  )
  suspend fun insert(
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
  )

  @Query(
    "UPDATE ProjectTable SET name = :name, activity = :activity, " +
        "lastBuildStatus = :lastBuildStatus, lastBuildLabel = :lastBuildLabel, lastBuildTime = :lastBuildTime, nextBuildTime = :nextBuildTime, webUrl = :webUrl, feedUrl = :feedUrl,username = :username, password = :password WHERE id = :id"
  )
  suspend fun update(
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
  )

  @Query("DELETE FROM ProjectTable WHERE id = :id;")
  suspend fun delete(id: Long)

  @Query("UPDATE ProjectTable SET isMuted=:isMuted, mutedUntil=:mutedUntil WHERE id = :id;")
  suspend fun updateMute(isMuted: Boolean, mutedUntil: ZonedDateTime?, id: Long)

  @Query("SELECT * FROM ProjectTable ORDER BY lastBuildStatus DESC, lastBuildTime DESC, id")
  fun selectAll(): Flow<List<ProjectTable>>

  @Query("SELECT * FROM ProjectTable WHERE id = :id")
  fun selectById(id: Long): ProjectTable

}