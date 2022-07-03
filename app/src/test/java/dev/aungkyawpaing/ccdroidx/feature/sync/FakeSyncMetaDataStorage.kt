package dev.aungkyawpaing.ccdroidx.feature.sync

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FakeSyncMetaDataStorage @Inject constructor() : SyncMetaDataStorage, Flow<LastSyncedStatus?> {

  private var lastSyncedState: LastSyncedStatus? = null

  override suspend fun saveLastSyncedTime(lastSyncedState: LastSyncedStatus) {
    this.lastSyncedState = lastSyncedState
  }

  override fun getLastSyncedTime(): Flow<LastSyncedStatus?> {
    return flowOf(lastSyncedState)
  }

  override suspend fun collect(collector: FlowCollector<LastSyncedStatus?>) {
    collector.emit(lastSyncedState)
  }

}