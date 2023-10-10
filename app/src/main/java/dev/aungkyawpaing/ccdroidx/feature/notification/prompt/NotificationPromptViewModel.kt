package dev.aungkyawpaing.ccdroidx.feature.notification.prompt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aungkyawpaing.ccdroidx.data.ProjectRepo
import dev.aungkyawpaing.ccdroidx.feature.notification.prompt.permssionflow.NotificationPermissionFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.Clock
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class NotificationPromptViewModel @Inject constructor(
  projectRepo: ProjectRepo,
  notificationsPermissionFlow: NotificationPermissionFlow,
  private val notificationDismissStore: NotificationDismissStore,
  private val clock: Clock
) : ViewModel() {

  val promptIsVisible: StateFlow<Boolean> = combine(
    projectRepo.getAll(),
    notificationDismissStore.getDismissTimeStamp(),
    notificationsPermissionFlow.getFlow(),
  ) { projectList, dismissTimeStamp, isPermissionGranted ->
    return@combine Triple(projectList, dismissTimeStamp, isPermissionGranted)
  }.map { (projectList, dismissTimeStamp, isPermissionGranted) ->
    val thereIsAtLeastOneProject = projectList.isNotEmpty()
    val lastDismissTimeNotWithin14Days =
      dismissTimeStamp == null || LocalDateTime.now(clock).minusDays(14)
        .isAfter(dismissTimeStamp)

    return@map thereIsAtLeastOneProject && lastDismissTimeNotWithin14Days && !isPermissionGranted
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

  fun onDismissClick() {
    viewModelScope.launch {
      notificationDismissStore.saveDismissTimeStamp(LocalDateTime.now(clock))
    }
  }

}