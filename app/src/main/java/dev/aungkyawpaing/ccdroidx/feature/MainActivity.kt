package dev.aungkyawpaing.ccdroidx.feature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.*
import dagger.hilt.android.AndroidEntryPoint
import dev.aungkyawpaing.ccdroidx.R
import dev.aungkyawpaing.ccdroidx.databinding.ActivityMainBinding
import dev.aungkyawpaing.ccdroidx.feature.sync.SyncProjectWorker
import java.time.Duration
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setUpSyncWorker()
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