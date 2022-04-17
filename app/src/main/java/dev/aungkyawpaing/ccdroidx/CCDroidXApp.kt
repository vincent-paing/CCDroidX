package dev.aungkyawpaing.ccdroidx

import android.app.Application
import com.google.android.material.color.DynamicColors

class CCDroidXApp : Application() {

  override fun onCreate() {
    super.onCreate()
    DynamicColors.applyToActivitiesIfAvailable(this)
  }
}