package dev.aungkyawpaing.ccdroidx

import android.content.Context
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.Wearable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

  @Provides
  @Singleton
  fun provideCapabilityClient(
    @ApplicationContext context: Context
  ): CapabilityClient {
    return Wearable.getCapabilityClient(context)
  }
}