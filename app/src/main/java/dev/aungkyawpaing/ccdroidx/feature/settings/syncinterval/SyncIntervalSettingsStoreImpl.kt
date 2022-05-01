package dev.aungkyawpaing.ccdroidx.feature.settings.syncinterval

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SyncIntervalSettingsStoreImpl
@Inject constructor(
  @ApplicationContext private val context: Context
) : SyncIntervalSettingsStore {

  private val Context.syncIntervalDataStore: DataStore<SyncIntervalProto?> by dataStore(
    fileName = "synced_interval_settings.pb",
    serializer = SyncIntervalProtoSerializer
  )


  private val defaultSyncInterval = SyncInterval(
    value = 15,
    timeUnit = SyncIntervalTimeUnit.MINUTES
  )

  override suspend fun setSyncInterval(syncInterval: SyncInterval) {
    context.syncIntervalDataStore.updateData { _ ->
      SyncIntervalProto(
        value_ = syncInterval.value,
        timeUnit = when (syncInterval.timeUnit) {
          SyncIntervalTimeUnit.MINUTES -> SyncIntervalProto.TimeUnitProto.MINUTES
          SyncIntervalTimeUnit.HOUR -> SyncIntervalProto.TimeUnitProto.HOURS
          SyncIntervalTimeUnit.DAY -> SyncIntervalProto.TimeUnitProto.DAYS
        }
      )
    }
  }

  override fun getSyncInterval(): Flow<SyncInterval> {
    return context.syncIntervalDataStore.data.map { proto ->
      if (proto == null) return@map defaultSyncInterval
      SyncInterval(
        value = proto.value_,
        timeUnit = when (proto.timeUnit) {
          SyncIntervalProto.TimeUnitProto.MINUTES -> SyncIntervalTimeUnit.MINUTES
          SyncIntervalProto.TimeUnitProto.HOURS -> SyncIntervalTimeUnit.HOUR
          SyncIntervalProto.TimeUnitProto.DAYS -> SyncIntervalTimeUnit.DAY
        }
      )
    }
  }

}