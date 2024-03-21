package dev.aungkyawpaing.ccdroidx

import android.app.Application
import androidx.work.Configuration
import com.google.android.material.color.DynamicColors
import dagger.hilt.android.HiltAndroidApp
import dev.aungkyawpaing.ccdroidx.work.MyWorkerFactory
import dev.shreyaspatil.permissionFlow.PermissionFlow
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
    FirebaseAnalytics.init()

    DynamicColors.applyToActivitiesIfAvailable(this)
    PermissionFlow.init(this)
  }

  override val workManagerConfiguration: Configuration
    get() = Configuration.Builder()
      .setWorkerFactory(workerFactory)
      .build()


}