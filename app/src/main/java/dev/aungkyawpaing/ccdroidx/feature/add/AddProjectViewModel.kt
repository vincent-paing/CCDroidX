package dev.aungkyawpaing.ccdroidx.feature.add

import androidx.databinding.ObservableBoolean
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
import dev.aungkyawpaing.ccdroidx.feature.add.feedurlvalidation.FeedUrlValidationResult
import dev.aungkyawpaing.ccdroidx.feature.add.passwordvalidation.PasswordValidationResult
import dev.aungkyawpaing.ccdroidx.feature.add.usernamevalidation.UsernameValidationResult
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
  private val addProjectInputValidator: AddProjectInputValidator,
  private val dispatcherProvider: DispatcherProvider
) : ObservableViewModel() {

  private val _isLoadingLiveData = MutableLiveData<Boolean>()
  val isLoadingLiveData: LiveData<Boolean> get() = _isLoadingLiveData

  val errorLiveEvent = SingleLiveEvent<String>()
  val showProjectListLiveEvent = SingleLiveEvent<List<Project>>()

  val dismissLiveEvent = SingleLiveEvent<Unit>()

  val _feedUrl = ObservableField("")
  private val feedUrl get() = _feedUrl.get() ?: ""

  val _requireAuth = ObservableBoolean(false)
  private val requireAuth get() = _requireAuth.get()

  val _username = ObservableField("")
  private val username get() = _username.get() ?: ""

  val _password = ObservableField("")
  private val password get() = _password.get() ?: ""

  val feedUrlValidationResult = ObservableField(FeedUrlValidationResult.CORRECT)
  val usernameValidationResult = ObservableField(UsernameValidationResult.CORRECT)
  val passwordValidationResult = ObservableField(PasswordValidationResult.CORRECT)


  fun onClickNext() {
    viewModelScope.launch {
      _isLoadingLiveData.postValue(true)
      feedUrlValidationResult.set(addProjectInputValidator.validateFeedUrl(feedUrl))

      if (requireAuth) {
        usernameValidationResult.set(addProjectInputValidator.validateUsername(username))
        passwordValidationResult.set(addProjectInputValidator.validatePassword(password))
      } else {
        usernameValidationResult.set(UsernameValidationResult.CORRECT)
        passwordValidationResult.set(PasswordValidationResult.CORRECT)
      }

      if (feedUrlValidationResult.get() == FeedUrlValidationResult.CORRECT &&
        usernameValidationResult.get() == UsernameValidationResult.CORRECT &&
        passwordValidationResult.get() == PasswordValidationResult.CORRECT
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
      dismissLiveEvent.postValue(Unit)
    }

  }
}