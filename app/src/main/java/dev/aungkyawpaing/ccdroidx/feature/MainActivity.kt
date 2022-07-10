package dev.aungkyawpaing.ccdroidx.feature

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsClient
import dagger.hilt.android.AndroidEntryPoint
import dev.aungkyawpaing.ccdroidx.R
import dev.aungkyawpaing.ccdroidx.feature.browser.openInBrowser
import dev.aungkyawpaing.ccdroidx.feature.sync.SyncWorkerScheduler

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  companion object {
    const val INTENT_EXTRA_URL = "url"
  }

  private val viewModel: MainViewModel by viewModels()
  private val syncWorkerScheduler by lazy {
    SyncWorkerScheduler(applicationContext)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    warmupBrowser()

    intent.getStringExtra(INTENT_EXTRA_URL)?.let { url ->
      openInBrowser(this, url)
    }

    viewModel.syncIntervalLiveData.observe(this) { syncInterval ->
      syncWorkerScheduler.removeExistingAndScheduleWorker(syncInterval)
    }
  }

  private fun warmupBrowser() {
    val browserPackageName = CustomTabsClient.getPackageName(this, null) ?: return
    CustomTabsClient.connectAndInitialize(this, browserPackageName)
  }


}