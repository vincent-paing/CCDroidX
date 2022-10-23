package dev.aungkyawpaing.wear.datalayer

import android.content.Context
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.Wearable
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.aungkyawpaing.ccdroidx.common.Project
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedWearDataLayer @Inject constructor(
  private val miniProjectSerializer: MiniProjectSerializer,
  @ApplicationContext private val context: Context,
) {

  companion object {
    private const val DATA_PATH = "/data"
    private const val PROJECTS_KEY = "projects"
  }

  private val dataClient = Wearable.getDataClient(context)

  suspend fun updateDataItems(projectList: List<Project>) {
    val result = runCatching {
      val jsonString = miniProjectSerializer.serializeProjects(projectList)

      val request = PutDataMapRequest.create(DATA_PATH).apply {
        dataMap.putString(PROJECTS_KEY, jsonString)
      }.asPutDataRequest().setUrgent()

      dataClient.putDataItem(request).await()
    }

    if (result.exceptionOrNull() != null) {
      Timber.e(result.exceptionOrNull())
    }
  }

  fun getDataItems(dataEvents: DataEventBuffer): List<MiniProject> {
    dataEvents.forEach { dataEvent ->
      val dataItem = dataEvent.dataItem
      when (dataItem.uri.path) {
        DATA_PATH -> {
          val dataMap = DataMapItem.fromDataItem(dataItem).dataMap
          val jsonString = dataMap.getString(PROJECTS_KEY) ?: return emptyList()
          return miniProjectSerializer.deserializeProjects(jsonString)
        }
      }
    }
    return emptyList()
  }

}