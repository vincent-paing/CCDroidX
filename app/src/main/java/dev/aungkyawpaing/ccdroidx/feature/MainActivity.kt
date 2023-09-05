package dev.aungkyawpaing.ccdroidx.feature

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsClient
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import dev.aungkyawpaing.ccdroidx.feature.browser.openInBrowser
import dev.aungkyawpaing.ccdroidx.feature.settings.Settings
import dev.aungkyawpaing.ccdroidx.feature.settings.settingsDataStore
import dev.aungkyawpaing.ccdroidx.feature.sync.SyncWorkerScheduler
import dev.shreyaspatil.permissionFlow.PermissionFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.time.Duration

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
    setContent {
      CCDroidXApp()
    }

    warmupBrowser()
    setupSyncWorker()
    handleIntent(intent)
  }

  private fun setupSyncWorker() {
    lifecycleScope.launch {
      val syncInterval = settingsDataStore.data.firstOrNull()?.get(Settings.KEY_SYNC_INTERVAL)
      val duration = if (syncInterval != null) {
        Duration.ofMinutes(syncInterval)
      } else {
        Settings.DEFAULT_SYNC_INTERVAL
      }
      syncWorkerScheduler.removeExistingAndScheduleWorker(duration)
    }
  }

  @SuppressLint("MissingSuperCall")
  override fun onNewIntent(intent: Intent?) {
    super.onNewIntent(intent)
    handleIntent(intent ?: return)
  }

  private fun handleIntent(intent: Intent) {

    intent.getStringExtra(INTENT_EXTRA_URL)?.let { url ->
      lifecycleScope.launch {
        openInBrowser(this@MainActivity, url)
      }
      return
    }

    val deepLinkUri = intent.data ?: return
    val host = deepLinkUri.host ?: ""

    val path = try {
      deepLinkUri.path?.substring(1) ?: ""
    } catch (exception: IndexOutOfBoundsException) {
      return
    }

    if (host == "project" && path.isNotEmpty()) {
      lifecycleScope.launch {
        val projectId = path.toLongOrNull() ?: return@launch
        val url = viewModel.getProjectUrlById(projectId) ?: return@launch
        openInBrowser(this@MainActivity, url)
      }
    }
  }

  private fun warmupBrowser() {
    val browserPackageName = CustomTabsClient.getPackageName(this, null) ?: return
    CustomTabsClient.connectAndInitialize(this, browserPackageName)
  }

  override fun onResume() {
    super.onResume()
    PermissionFlow.getInstance().notifyPermissionsChanged(Manifest.permission.POST_NOTIFICATIONS)
  }

}