package dev.aungkyawpaing.ccdroidx.feature.sync

import kotlinx.coroutines.flow.Flow
import java.time.ZonedDateTime

interface SyncMetaDataStorage {

  suspend fun saveLastSyncedTime(zonedDateTime: ZonedDateTime)

  fun getLastSyncedTime(): Flow<ZonedDateTime?>
}