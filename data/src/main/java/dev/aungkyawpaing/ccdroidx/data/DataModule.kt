package dev.aungkyawpaing.ccdroidx.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

  @Provides
  @Singleton
  fun provideCryptography(): Cryptography {
    return Cryptography("CCDROID")
  }
}