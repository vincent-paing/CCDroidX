package dev.aungkyawpaing.ccdroidx.feature.sync

import kotlinx.coroutines.flow.Flow
import java.time.ZonedDateTime

interface SyncMetaDataStorage {

  suspend fun saveLastSyncedTime(lastSyncedState: LastSyncedStatus)

  fun getLastSyncedTime(): Flow<LastSyncedStatus?>
}