package dev.aungkyawpaing.ccdroidx.feature.sync

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.ZonedDateTime
import javax.inject.Inject

class FakeSyncMetaDataStorage @Inject constructor() : SyncMetaDataStorage {

  private var lastSyncedDateTime: ZonedDateTime? = null

  override suspend fun saveLastSyncedTime(zonedDateTime: ZonedDateTime) {
    lastSyncedDateTime = zonedDateTime
  }

  override fun getLastSyncedTime(): Flow<ZonedDateTime?> {
    return flowOf(lastSyncedDateTime)
  }
}