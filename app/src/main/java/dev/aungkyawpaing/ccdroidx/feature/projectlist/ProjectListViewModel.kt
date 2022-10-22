package dev.aungkyawpaing.ccdroidx.feature.projectlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aungkyawpaing.ccdroidx.common.coroutine.DispatcherProvider
import dev.aungkyawpaing.ccdroidx.data.Project
import dev.aungkyawpaing.ccdroidx.data.ProjectRepo
import dev.aungkyawpaing.ccdroidx.feature.sync.SyncMetaDataStorage
import dev.aungkyawpaing.ccdroidx.feature.sync.SyncProjects
import dev.aungkyawpaing.ccdroidx.common.livedata.SingleLiveEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProjectListViewModel @Inject constructor(
  private val projectRepo: ProjectRepo,
  private val syncProjects: SyncProjects,
  syncMetaDataStorage: SyncMetaDataStorage,
  private val dispatcherProvider: DispatcherProvider,
) : ViewModel() {

  val projectListLiveData = projectRepo.getAll().asLiveData()

  val lastSyncedLiveData = syncMetaDataStorage.getLastSyncedTime().asLiveData()

  val onProgressSyncFinishEvent = SingleLiveEvent<Boolean>()

  fun onDeleteProject(project: Project) {
    viewModelScope.launch {
      withContext(dispatcherProvider.io()) {
        projectRepo.delete(project.id)
      }
    }
  }

  fun onPressSync() {
    viewModelScope.launch {
      withContext(dispatcherProvider.io()) {
        syncProjects.sync(
          onProjectSynced = { _, _ ->
            onProgressSyncFinishEvent.postValue(true)
          }
        )
      }
    }
  }

  fun onToggleMute(project: Project) {
    viewModelScope.launch {
      if (project.isMuted) {
        projectRepo.unmuteProject(project.id)
      } else {
        projectRepo.muteProject(project.id)
      }
    }
  }

  fun clearOnProgressSyncedEvent() {
    viewModelScope.launch {
      delay(500)
      onProgressSyncFinishEvent.postValue(false)
    }
  }
}