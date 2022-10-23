package dev.aungkyawpaing.ccdroidx

import android.content.ComponentName
import androidx.wear.watchface.complications.datasource.ComplicationDataSourceUpdateRequester
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.WearableListenerService
import dagger.hilt.android.AndroidEntryPoint
import dev.aungkyawpaing.ccdroidx.complication.FailingProjectCountComplicationDataSourceService
import dev.aungkyawpaing.wear.datalayer.SharedWearDataLayer
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class DataLayerListenerService : WearableListenerService() {

  @Inject
  lateinit var projectDataStore: ProjectDataStore

  @Inject
  lateinit var sharedWearDataLayer: SharedWearDataLayer

  private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

  override fun onDataChanged(dataEvents: DataEventBuffer) {
    super.onDataChanged(dataEvents)

    scope.launch {
      val miniProjectList = sharedWearDataLayer.getDataItems(dataEvents)
      projectDataStore.putProjectList(miniProjectList)
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