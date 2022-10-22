package dev.aungkyawpaing.ccdroidx

import android.content.ComponentName
import androidx.wear.watchface.complications.datasource.ComplicationDataSourceUpdateRequester
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.WearableListenerService
import dagger.hilt.android.AndroidEntryPoint
import dev.aungkyawpaing.ccdroidx.complication.FailingProjectCountComplicationDataSourceService
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class DataLayerListenerService : WearableListenerService() {

  companion object {
    private const val DATA_PATH = "/data"
    private const val FAIL_COUNT_KEY = "fail_count"
  }

  @Inject
  lateinit var projectDataStore: ProjectDataStore

  private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

  override fun onDataChanged(dataEvents: DataEventBuffer) {
    super.onDataChanged(dataEvents)

    Timber.i("onDataChanged")
    dataEvents.forEach { dataEvent ->
      val dataItem = dataEvent.dataItem
      when (dataItem.uri.path) {
        DATA_PATH -> {
          scope.launch {
            val dataMap = DataMapItem.fromDataItem(dataItem).dataMap
            val failingProjectCount = dataMap.getLong(FAIL_COUNT_KEY)
            projectDataStore.putFailingProjectCount(failingProjectCount)
          }
        }
      }
    }
    val complicationDataSourceUpdateRequester =
      ComplicationDataSourceUpdateRequester.create(
        context = this,
        complicationDataSourceComponent = ComponentName(
          this,
          FailingProjectCountComplicationDataSourceService::class.java
        )
      )
    complicationDataSourceUpdateRequester.requestUpdateAll()
  }

  override fun onDestroy() {
    super.onDestroy()
    scope.cancel()
  }

}