package dev.aungkyawpaing.ccdroidx

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.aungkyawpaing.ccdroidx.utils.security.Cryptography
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

  @Provides
  @Singleton
  fun provideCryptography(): Cryptography {
    return Cryptography("CCDROID")
  }
}