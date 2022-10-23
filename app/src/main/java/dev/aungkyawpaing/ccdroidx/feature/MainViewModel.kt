package dev.aungkyawpaing.ccdroidx.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aungkyawpaing.ccdroidx.data.ProjectRepo
import dev.aungkyawpaing.ccdroidx.feature.settings.syncinterval.SyncIntervalSettingsStore
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  syncIntervalSettingsStore: SyncIntervalSettingsStore,
  private val projectRepo: ProjectRepo
) : ViewModel() {

  val syncIntervalLiveData = syncIntervalSettingsStore.getSyncInterval().asLiveData()

  suspend fun getProjectUrlById(projectId: Long): String? {
    return projectRepo.getById(projectId).firstOrNull()?.webUrl
  }

}