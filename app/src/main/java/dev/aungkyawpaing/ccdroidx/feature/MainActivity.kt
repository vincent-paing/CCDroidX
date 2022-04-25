package dev.aungkyawpaing.ccdroidx.feature

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsClient
import androidx.work.*
import dagger.hilt.android.AndroidEntryPoint
import dev.aungkyawpaing.ccdroidx.R
import dev.aungkyawpaing.ccdroidx.feature.browser.OpenInBrowser
import dev.aungkyawpaing.ccdroidx.feature.sync.SyncProjectWorker
import java.time.Duration

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  companion object {
    const val INTENT_EXTRA_URL = "url"
  }

  private val openInBrowser = OpenInBrowser()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setUpSyncWorker()
    warmupBrowser()

    intent.getStringExtra(INTENT_EXTRA_URL)?.let { url ->
      openInBrowser.openInBrowser(this, url)
    }
  }

  private fun warmupBrowser() {
    val browserPackageName = CustomTabsClient.getPackageName(this, null) ?: return
    CustomTabsClient.connectAndInitialize(this, browserPackageName)
  }

  private fun setUpSyncWorker() {
    val constraints = Constraints.Builder()
      .setRequiredNetworkType(NetworkType.CONNECTED)
      .setRequiresBatteryNotLow(true)
      .build()


    val syncWorkRequest =
      PeriodicWorkRequestBuilder<SyncProjectWorker>(
        Duration.ofMinutes(15),
        Duration.ofMinutes(15),
      )
        .addTag(SyncProjectWorker.TAG)
        .setConstraints(constraints)
        .build()

    WorkManager.getInstance(applicationContext)
      .enqueueUniquePeriodicWork(
        SyncProjectWorker.TAG,
        ExistingPeriodicWorkPolicy.KEEP,
        syncWorkRequest
      )

  }


}