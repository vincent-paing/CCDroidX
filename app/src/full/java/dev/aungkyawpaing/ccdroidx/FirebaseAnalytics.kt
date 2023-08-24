package dev.aungkyawpaing.ccdroidx

import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase

object FirebaseAnalytics  {

  fun init() {
    Firebase.crashlytics.setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG)
    Firebase.analytics.setAnalyticsCollectionEnabled(!BuildConfig.DEBUG)
  }

}