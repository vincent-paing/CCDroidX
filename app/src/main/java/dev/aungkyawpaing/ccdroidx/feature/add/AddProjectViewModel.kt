package dev.aungkyawpaing.ccdroidx.feature.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aungkyawpaing.ccdroidx.api.InvalidUrlException
import dev.aungkyawpaing.ccdroidx.api.NetworkException
import dev.aungkyawpaing.ccdroidx.coroutine.DispatcherProvider
import dev.aungkyawpaing.ccdroidx.data.Project
import dev.aungkyawpaing.ccdroidx.data.ProjectRepo
import dev.aungkyawpaing.ccdroidx.exception.MapNetworkExceptionToMessage
import dev.aungkyawpaing.ccdroidx.utils.livedata.SingleLiveEvent
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddProjectViewModel @Inject constructor(
  private val projectRepo: ProjectRepo,
  private val mapNetworkExceptionToMessage: MapNetworkExceptionToMessage,
  private val addProjectErrorMessages: AddProjectErrorMessages,
  private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

  private val _isLoadingLiveData = MutableLiveData<Boolean>()
  val isLoadingLiveData: LiveData<Boolean> get() = _isLoadingLiveData

  val errorLiveEvent = SingleLiveEvent<String>()
  val projectListLiveEvent = SingleLiveEvent<List<Project>>()

  val dismissLiveEvent = SingleLiveEvent<Unit>()

  fun onClickNext(feedUrl: String) {
    viewModelScope.launch {
      _isLoadingLiveData.postValue(true)
      try {
        val projectList = projectRepo.fetchRepo(feedUrl)
        projectListLiveEvent.postValue(projectList)
      } catch (networkException: NetworkException) {
        errorLiveEvent.postValue(mapNetworkExceptionToMessage.getMessage(networkException))
      } catch (invalidUrlException: InvalidUrlException) {
        errorLiveEvent.postValue(addProjectErrorMessages.invalidUrlMessage())
      }
      _isLoadingLiveData.postValue(false)
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