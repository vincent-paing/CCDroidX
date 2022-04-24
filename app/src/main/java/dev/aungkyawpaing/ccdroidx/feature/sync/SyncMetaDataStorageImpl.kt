package dev.aungkyawpaing.ccdroidx.feature.sync

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.inject.Inject
import javax.inject.Singleton

private val Context.syncedMetaDataStore: DataStore<Preferences> by preferencesDataStore(name = "sync_metadata")

class SyncMetaDataStorageImpl @Inject constructor(
  @ApplicationContext private val context: Context
) : SyncMetaDataStorage {

  companion object {
    private val KEY_LAST_SYNCED_DATE_TIME = longPreferencesKey("last_synced_time")
  }

  override suspend fun saveLastSyncedTime(zonedDateTime: ZonedDateTime) {
    context.syncedMetaDataStore.edit { store ->
      store[KEY_LAST_SYNCED_DATE_TIME] = zonedDateTime.toInstant().toEpochMilli()
    }
  }

  override fun getLastSyncedTime(): Flow<ZonedDateTime?> {
    return context.syncedMetaDataStore.data.map { store ->
      val instant = Instant.ofEpochMilli(store[KEY_LAST_SYNCED_DATE_TIME] ?: return@map null)
      ZonedDateTime.ofInstant(instant, ZoneId.systemDefault())
    }
  }
}