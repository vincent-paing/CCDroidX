package dev.aungkyawpaing.ccdroidx

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import com.google.android.material.color.DynamicColors
import dagger.hilt.android.HiltAndroidApp
import dev.aungkyawpaing.ccdroidx.work.MyWorkerFactory
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class CCDroidXApp : Application(), Configuration.Provider {

  @Inject
  lateinit var workerFactory: MyWorkerFactory

  override fun onCreate() {
    super.onCreate()

    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
    DynamicColors.applyToActivitiesIfAvailable(this)
  }

  override fun getWorkManagerConfiguration() =
    Configuration.Builder()
      .setWorkerFactory(workerFactory)
      .build()

}