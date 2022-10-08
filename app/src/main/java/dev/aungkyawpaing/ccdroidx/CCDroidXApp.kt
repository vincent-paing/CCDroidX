package dev.aungkyawpaing.ccdroidx

import android.app.Application
import androidx.work.Configuration
import com.google.android.material.color.DynamicColors
import com.google.firebase.crashlytics.FirebaseCrashlytics
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
    FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG)
    DynamicColors.applyToActivitiesIfAvailable(this)
  }

  override fun getWorkManagerConfiguration() =
    Configuration.Builder()
      .setWorkerFactory(workerFactory)
      .build()

}