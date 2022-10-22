package dev.aungkyawpaing.ccdroidx.feature.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aungkyawpaing.ccdroidx.api.InvalidUrlException
import dev.aungkyawpaing.ccdroidx.api.NetworkException
import dev.aungkyawpaing.ccdroidx.api.UnsupportedServerException
import dev.aungkyawpaing.ccdroidx.coroutine.DispatcherProvider
import dev.aungkyawpaing.ccdroidx.data.Project
import dev.aungkyawpaing.ccdroidx.data.ProjectRepo
import dev.aungkyawpaing.ccdroidx.exception.MapNetworkExceptionToMessage
import dev.aungkyawpaing.ccdroidx.feature.add.feedurlvalidation.FeedUrlValidationResult
import dev.aungkyawpaing.ccdroidx.feature.add.passwordvalidation.PasswordValidationResult
import dev.aungkyawpaing.ccdroidx.feature.add.usernamevalidation.UsernameValidationResult
import dev.aungkyawpaing.ccdroidx.utils.livedata.SingleLiveEvent
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddProjectViewModel @Inject constructor(
  private val projectRepo: ProjectRepo,
  private val mapNetworkExceptionToMessage: MapNetworkExceptionToMessage,
  private val addProjectErrorMessages: AddProjectErrorMessages,
  private val addProjectInputValidator: AddProjectInputValidator,
  private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

  private val _isLoadingLiveData = MutableLiveData<Boolean>()
  val isLoadingLiveData: LiveData<Boolean> get() = _isLoadingLiveData

  val errorLiveEvent = SingleLiveEvent<String>()
  val showProjectListLiveEvent = MutableLiveData<List<Project>?>()

  val feedUrlValidationResult = MutableLiveData(FeedUrlValidationResult.CORRECT)
  val usernameValidationResult = MutableLiveData(UsernameValidationResult.CORRECT)
  val passwordValidationResult = MutableLiveData(PasswordValidationResult.CORRECT)

  fun onClickNext(feedUrl: String, requireAuth: Boolean, username: String, password: String) {
    viewModelScope.launch {
      _isLoadingLiveData.postValue(true)
      feedUrlValidationResult.postValue(addProjectInputValidator.validateFeedUrl(feedUrl))

      if (requireAuth) {
        usernameValidationResult.postValue(addProjectInputValidator.validateUsername(username))
        passwordValidationResult.postValue(addProjectInputValidator.validatePassword(password))
      } else {
        usernameValidationResult.postValue(UsernameValidationResult.CORRECT)
        passwordValidationResult.postValue(PasswordValidationResult.CORRECT)
      }

      if (feedUrlValidationResult.value == FeedUrlValidationResult.CORRECT &&
        usernameValidationResult.value == UsernameValidationResult.CORRECT &&
        passwordValidationResult.value == PasswordValidationResult.CORRECT
      ) {
        try {
          val projectList = projectRepo.fetchRepo(
            url = feedUrl,
            username = if (requireAuth) username else null,
            password = if (requireAuth) password else null,
          )
          showProjectListLiveEvent.postValue(projectList)
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
    }
  }

  fun onDismissSelectProject() {
    showProjectListLiveEvent.postValue(null)
  }
}