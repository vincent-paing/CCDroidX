package dev.aungkyawpaing.ccdroidx.feature.sync

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.aungkyawpaing.ccdroidx.SyncedStateProto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.inject.Inject


class SyncMetaDataStorageImpl @Inject constructor(
  @ApplicationContext private val context: Context
) : SyncMetaDataStorage {

  val Context.lastSyncedStateDataStore: DataStore<SyncedStateProto?> by dataStore(
    fileName = "last_synced_state.pb",
    serializer = SyncedStateProtoSerializer
  )


  override suspend fun saveLastSyncedTime(lastSyncedState: LastSyncedStatus) {
    context.lastSyncedStateDataStore.updateData { _ ->
      SyncedStateProto(
        lastSyncedDateTime = lastSyncedState.lastSyncedDateTime.toInstant().toEpochMilli(),
        status = when (lastSyncedState.lastSyncedState) {
          LastSyncedState.SYNCING -> SyncedStateProto.Status.SYNCING
          LastSyncedState.SUCCESS -> SyncedStateProto.Status.SUCCESS
          LastSyncedState.FAILED -> SyncedStateProto.Status.FAILED
        },
        errorCode = lastSyncedState.errorCode
      )
    }
  }

  override fun getLastSyncedTime(): Flow<LastSyncedStatus?> {
    return context.lastSyncedStateDataStore.data.map { value ->
      if (value != null) {
        return@map LastSyncedStatus(
          lastSyncedDateTime = ZonedDateTime.ofInstant(
            Instant.ofEpochMilli(value.lastSyncedDateTime),
            ZoneId.systemDefault()
          ),
          lastSyncedState = when (value.status) {
            SyncedStateProto.Status.SYNCING -> LastSyncedState.SYNCING
            SyncedStateProto.Status.SUCCESS -> LastSyncedState.SUCCESS
            SyncedStateProto.Status.FAILED -> LastSyncedState.FAILED
          },
          errorCode = value.errorCode
        )
      } else {
        return@map null
      }
    }
  }
}