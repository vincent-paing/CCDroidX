package dev.aungkyawpaing.ccdroidx.feature.notification.prompt

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aungkyawpaing.ccdroidx.data.ProjectRepo
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.Clock
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class NotificationPromptViewModel @Inject constructor(
  projectRepo: ProjectRepo,
  private val notificationDismissStore: NotificationDismissStore,
  private val notificationsEnableCheck: NotificationsEnableCheck,
  private val clock: Clock
) : ViewModel() {

  val promptIsVisibleLiveData: LiveData<Boolean> =
    projectRepo.getAll()
      .combine(notificationDismissStore.getDismissTimeStamp()) { projectList, dismissTimeStamp ->
        return@combine Pair(projectList, dismissTimeStamp)
      }.map { (projectList, dismissTimeStamp) ->

        val thereIsAtLeastOneProject = projectList.isNotEmpty()
        val lastDismissTimeNotWithin14Days =
          dismissTimeStamp == null || LocalDateTime.now(clock).minusDays(14)
            .isAfter(dismissTimeStamp)
        val notificationHasBeenDisabled = !notificationsEnableCheck.areNotificationsEnabled()

        return@map thereIsAtLeastOneProject && lastDismissTimeNotWithin14Days && notificationHasBeenDisabled
      }.asLiveData()

  fun onDismissClick() {
    viewModelScope.launch {
      notificationDismissStore.saveDismissTimeStamp(LocalDateTime.now(clock))
    }
  }

}