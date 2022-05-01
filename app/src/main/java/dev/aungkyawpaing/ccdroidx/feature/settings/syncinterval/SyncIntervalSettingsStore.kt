package dev.aungkyawpaing.ccdroidx.feature.settings.syncinterval

import kotlinx.coroutines.flow.Flow

interface SyncIntervalSettingsStore {

  suspend fun setSyncInterval(syncInterval: SyncInterval)

  fun getSyncInterval(): Flow<SyncInterval>
}