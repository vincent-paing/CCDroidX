package dev.aungkyawpaing.ccdroidx.feature.settings.syncinterval

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SyncIntervalSettingsModule {

  @Binds
  @Singleton
  abstract fun bindSyncIntervalSettingsStore(
    syncIntervalSettingsStore: SyncIntervalSettingsStoreImpl
  ): SyncIntervalSettingsStore


}