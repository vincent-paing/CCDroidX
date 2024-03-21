package dev.aungkyawpaing.ccdroidx.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.aungkyawpaing.ccdroidx.common.BuildState
import dev.aungkyawpaing.ccdroidx.common.BuildStatus
import java.time.ZonedDateTime

@Entity(tableName = "ProjectTable")
data class ProjectTable(
  // TODO: Have to put nullable for id because of sqldelight => room migration.
  //  Add migration script to make id non nullable
  @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long?,
  @ColumnInfo(name = "name") val name: String,
  @ColumnInfo(name = "activity") val activity: BuildState,
  @ColumnInfo(name = "lastBuildStatus") val lastBuildStatus: BuildStatus,
  @ColumnInfo(name = "lastBuildLabel") val lastBuildLabel: String?,
  @ColumnInfo(name = "lastBuildTime") val lastBuildTime: ZonedDateTime,
  @ColumnInfo(name = "nextBuildTime") val nextBuildTime: ZonedDateTime?,
  @ColumnInfo(name = "webUrl") val webUrl: String,
  @ColumnInfo(name = "feedUrl") val feedUrl: String,
  @ColumnInfo(name = "isMuted", defaultValue = "0") val isMuted: Boolean = false,
  @ColumnInfo(name = "mutedUntil", defaultValue = "NULL") val mutedUntil: ZonedDateTime? = null,
  @ColumnInfo(name = "username", defaultValue = "NULL") val username: String? = null,
  @ColumnInfo(name = "password", defaultValue = "NULL") val password: String? = null,
)
