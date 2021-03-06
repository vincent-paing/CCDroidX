package dev.aungkyawpaing.ccdroidx.feature.projectlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aungkyawpaing.ccdroidx.coroutine.DispatcherProvider
import dev.aungkyawpaing.ccdroidx.data.Project
import dev.aungkyawpaing.ccdroidx.data.ProjectRepo
import dev.aungkyawpaing.ccdroidx.feature.sync.SyncMetaDataStorage
import dev.aungkyawpaing.ccdroidx.feature.sync.SyncProjects
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
            //DO NOTHING
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
}