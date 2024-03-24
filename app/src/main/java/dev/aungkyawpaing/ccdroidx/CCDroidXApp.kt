package dev.aungkyawpaing.ccdroidx

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.google.android.material.color.DynamicColors
import dagger.hilt.android.HiltAndroidApp
import dev.shreyaspatil.permissionFlow.PermissionFlow
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class CCDroidXApp : Application(), Configuration.Provider {

  @Inject
  lateinit var workerFactory: HiltWorkerFactory

  override fun onCreate() {
    super.onCreate()

    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
    FirebaseAnalytics.init()

    DynamicColors.applyToActivitiesIfAvailable(this)
    PermissionFlow.init(this)
  }

  override val workManagerConfiguration: Configuration
    get() = Configuration.Builder()
      .setWorkerFactory(workerFactory)
      .build()


}