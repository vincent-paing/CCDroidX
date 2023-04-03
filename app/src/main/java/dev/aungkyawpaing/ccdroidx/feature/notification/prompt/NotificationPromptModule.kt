package dev.aungkyawpaing.ccdroidx.feature.notification.prompt

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NotificationPromptModule {

  @Binds
  @Singleton
  abstract fun bindNotificationDismissStore(
    syncIntervalSettingsStore: NotificationDismissStoreImpl
  ): NotificationDismissStore


}