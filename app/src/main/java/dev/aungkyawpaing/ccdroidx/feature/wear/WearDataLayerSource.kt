package dev.aungkyawpaing.ccdroidx.feature.wear

import android.content.Context
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.Wearable
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.aungkyawpaing.ccdroidx.data.ProjectRepo
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WearDataLayerSource @Inject constructor(
  private val projectRepo: ProjectRepo,
  @ApplicationContext private val context: Context
) {

  companion object {
    private const val DATA_PATH = "/data"
    private const val FAIL_COUNT_KEY = "fail_count"
  }

  private val dataClient = Wearable.getDataClient(context)

  suspend fun updateDataItems() {
    val result = runCatching {
      val failingCount = projectRepo.getFailingProjectCount()

      val request = PutDataMapRequest.create(DATA_PATH).apply {
        dataMap.putLong(FAIL_COUNT_KEY, failingCount)
      }.asPutDataRequest().setUrgent()

      dataClient.putDataItem(request).await()
    }

    if (result.exceptionOrNull() != null) {
      Timber.e(result.exceptionOrNull())
    }
  }

}