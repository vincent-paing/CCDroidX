package dev.aungkyawpaing.ccdroidx.feature.sync

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.ZonedDateTime
import javax.inject.Inject

class FakeSyncMetaDataStorage @Inject constructor() : SyncMetaDataStorage {

  private var lastSyncedState: LastSyncedStatus? = null

  override suspend fun saveLastSyncedTime(lastSyncedState: LastSyncedStatus) {
    this.lastSyncedState = lastSyncedState
  }

  override fun getLastSyncedTime(): Flow<LastSyncedStatus?> {
    return flowOf(lastSyncedState)
  }

}