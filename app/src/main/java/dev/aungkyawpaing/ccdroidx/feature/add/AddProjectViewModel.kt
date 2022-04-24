package dev.aungkyawpaing.ccdroidx.feature.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aungkyawpaing.ccdroidx.coroutine.DispatcherProvider
import dev.aungkyawpaing.ccdroidx.data.Project
import dev.aungkyawpaing.ccdroidx.data.ProjectRepo
import dev.aungkyawpaing.ccdroidx.utils.livedata.SingleLiveEvent
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddProjectViewModel @Inject constructor(
  private val projectRepo: ProjectRepo,
  private val dispatcherProvider: DispatcherProvider,
) : ViewModel() {

  private val _isLoadingLiveData = MutableLiveData<Boolean>()
  val isLoadingLiveData: LiveData<Boolean> get() = _isLoadingLiveData

  val projectListLiveEvent = SingleLiveEvent<List<Project>>()

  val dismissLiveEvent = SingleLiveEvent<Unit>()

  fun getProjectsFromFeed(feedUrl: String) {
    viewModelScope.launch {
      _isLoadingLiveData.postValue(true)
      val projectList = projectRepo.fetchRepo(feedUrl)
      _isLoadingLiveData.postValue(false)
      projectListLiveEvent.postValue(projectList)

    }
  }

  fun onSelectProject(project: Project) {
    viewModelScope.launch {
      withContext(dispatcherProvider.io()) {
        projectRepo.saveProject(project)
      }
      dismissLiveEvent.postValue(Unit)
    }

  }
}