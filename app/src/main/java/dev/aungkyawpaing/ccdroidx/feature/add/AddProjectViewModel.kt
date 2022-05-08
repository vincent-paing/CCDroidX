package dev.aungkyawpaing.ccdroidx.feature.add

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aungkyawpaing.ccdroidx.api.InvalidUrlException
import dev.aungkyawpaing.ccdroidx.api.NetworkException
import dev.aungkyawpaing.ccdroidx.api.UnsupportedServerException
import dev.aungkyawpaing.ccdroidx.coroutine.DispatcherProvider
import dev.aungkyawpaing.ccdroidx.data.Project
import dev.aungkyawpaing.ccdroidx.data.ProjectRepo
import dev.aungkyawpaing.ccdroidx.exception.MapNetworkExceptionToMessage
import dev.aungkyawpaing.ccdroidx.utils.databinding.ObservableViewModel
import dev.aungkyawpaing.ccdroidx.utils.livedata.SingleLiveEvent
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddProjectViewModel @Inject constructor(
  private val projectRepo: ProjectRepo,
  private val mapNetworkExceptionToMessage: MapNetworkExceptionToMessage,
  private val addProjectErrorMessages: AddProjectErrorMessages,
  private val addProjectFeedUrlValidation: AddProjectFeedUrlValidation,
  private val dispatcherProvider: DispatcherProvider
) : ObservableViewModel() {

  private val _isLoadingLiveData = MutableLiveData<Boolean>()
  val isLoadingLiveData: LiveData<Boolean> get() = _isLoadingLiveData

  val errorLiveEvent = SingleLiveEvent<String>()
  val projectListLiveEvent = SingleLiveEvent<List<Project>>()

  val dismissLiveEvent = SingleLiveEvent<Unit>()

  val _feedUrl = ObservableField("")
  private val feedUrl get() = _feedUrl.get() ?: ""
  val feedUrlValidationResult = ObservableField(FeedUrlValidationResult.CORRECT)

  fun onClickNext() {
    viewModelScope.launch {
      _isLoadingLiveData.postValue(true)
      val validation = addProjectFeedUrlValidation.validateProjectFeedUrl(feedUrl)
      feedUrlValidationResult.set(validation)
      if (validation == FeedUrlValidationResult.CORRECT) {
        try {
          val projectList = projectRepo.fetchRepo(feedUrl)
          projectListLiveEvent.postValue(projectList)
        } catch (networkException: NetworkException) {
          errorLiveEvent.postValue(mapNetworkExceptionToMessage.getMessage(networkException))
        } catch (invalidUrlException: InvalidUrlException) {
          errorLiveEvent.postValue(addProjectErrorMessages.invalidUrlMessage())
        } catch (unsupportedServerException: UnsupportedServerException) {
          errorLiveEvent.postValue(addProjectErrorMessages.unsupportedServer())
        }
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