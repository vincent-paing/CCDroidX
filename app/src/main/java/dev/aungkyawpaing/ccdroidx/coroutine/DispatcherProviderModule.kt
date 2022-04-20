package dev.aungkyawpaing.ccdroidx.coroutine

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DispatcherProviderModule {

  @Provides
  @Singleton
  fun provideDispatcherProvider(): DispatcherProvider {
    return DispatcherProviderImpl()
  }
}