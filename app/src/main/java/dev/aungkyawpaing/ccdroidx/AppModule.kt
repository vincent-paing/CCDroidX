package dev.aungkyawpaing.ccdroidx

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.time.Clock
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

  @Provides
  @Singleton
  fun provideClock(): Clock {
    return Clock.systemDefaultZone()
  }
}