package dev.aungkyawpaing.ccdroidx.feature.wear

import dev.aungkyawpaing.ccdroidx.data.ProjectRepo
import dev.aungkyawpaing.wear.datalayer.SharedWearDataLayer
import kotlinx.coroutines.flow.firstOrNull
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WearDataLayerSource @Inject constructor(
  private val sharedWearDataLayer: SharedWearDataLayer,
  private val projectRepo: ProjectRepo
) {

  suspend fun updateDataItems() {
    val result = runCatching {
      sharedWearDataLayer.updateDataItems(projectRepo.getAll().firstOrNull() ?: emptyList())
    }

    if (result.exceptionOrNull() != null) {
      Timber.e(result.exceptionOrNull())
    }
  }

}