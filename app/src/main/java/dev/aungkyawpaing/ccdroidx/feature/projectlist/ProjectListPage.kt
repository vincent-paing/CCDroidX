package dev.aungkyawpaing.ccdroidx.feature.projectlist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.aungkyawpaing.ccdroidx.R
import dev.aungkyawpaing.ccdroidx.common.Project
import dev.aungkyawpaing.ccdroidx.common.extensions.findActivity
import dev.aungkyawpaing.ccdroidx.feature.browser.openInBrowser
import dev.aungkyawpaing.ccdroidx.feature.destinations.AddProjectDialogScreenDestination
import dev.aungkyawpaing.ccdroidx.feature.notification.prompt.NotificationPrompt
import dev.aungkyawpaing.ccdroidx.feature.notification.prompt.NotificationPromptViewModel
import dev.aungkyawpaing.ccdroidx.feature.projectlist.component.ProjectList
import dev.aungkyawpaing.ccdroidx.feature.projectlist.component.ProjectListTopAppBar
import dev.aungkyawpaing.ccdroidx.feature.sync.LastSyncedStatus
import kotlinx.coroutines.launch
import java.time.Clock

@Composable
fun DeleteConfirmationDialog(
  onConfirmDelete: () -> Unit, onDismiss: () -> Unit
) {
  AlertDialog(onDismissRequest = onDismiss, title = {
    Text(text = stringResource(id = R.string.confirm_delete_title))
  }, text = {
    Text(text = stringResource(id = R.string.confirm_delete_message))
  }, confirmButton = {
    TextButton(onClick = {
      onConfirmDelete()
      onDismiss()
    }) {
      Text(stringResource(id = R.string.action_item_project_delete_project))
    }
  }, dismissButton = {
    TextButton(
      onClick = onDismiss
    ) {
      Text(stringResource(id = android.R.string.cancel))
    }
  })
}

@Composable
fun ProjectListPageContent(
  projectList: List<Project>,
  lastSyncedStatus: LastSyncedStatus?,
  onProgressSyncedEvent: Boolean,
  onToggleMute: (project: Project) -> Unit,
  onPressSync: () -> Unit,
  clearOnProgressSyncedEvent: () -> Unit,
  onDeleteProject: (project: Project) -> Unit,
  notificationPromptViewModel: NotificationPromptViewModel,
  navigator: DestinationsNavigator,
  clock: Clock = Clock.systemDefaultZone()
) {

  val context = LocalContext.current
  val scope = rememberCoroutineScope()

  Scaffold(topBar = {
    ProjectListTopAppBar(
      lastSyncedStatus,
      onProgressSyncedEvent,
      onPressSync,
      clearOnProgressSyncedEvent,
      navigator,
      clock
    )
  }) { contentPadding ->
    val deleteConfirmDialog = remember { mutableStateOf<Project?>(null) }

    ConstraintLayout(
      modifier = Modifier
        .padding(contentPadding)
        .fillMaxSize()
    ) {

      val (notificationPrompt, projectListComponent, fabAddProject) = createRefs()

      ProjectList(projectList = projectList, onOpenRepoClick = { project ->
        context.findActivity()?.let { activity ->
          scope.launch {
            openInBrowser(activity, project.webUrl)
          }
        }
      }, onDeleteClick = { project ->
        deleteConfirmDialog.value = project
      }, onToggleMute = onToggleMute, modifier = Modifier.constrainAs(projectListComponent) {
        end.linkTo(parent.end)
        start.linkTo(parent.start)
        bottom.linkTo(notificationPrompt.top)
        top.linkTo(parent.top)
        height = Dimension.fillToConstraints
        width = Dimension.fillToConstraints
      }, clock = clock
      )

      NotificationPrompt(
        notificationPromptViewModel = notificationPromptViewModel,
        modifier = Modifier.constrainAs(notificationPrompt) {
          end.linkTo(parent.end)
          start.linkTo(parent.start)
          bottom.linkTo(parent.bottom)
        })

      FloatingActionButton(onClick = {
        navigator.navigate(AddProjectDialogScreenDestination())
      }, modifier = Modifier
        .constrainAs(fabAddProject) {
          end.linkTo(parent.end)
          bottom.linkTo(notificationPrompt.top)
        }
        .padding(16.dp)) {
        Icon(
          Icons.Filled.Add, contentDescription = stringResource(R.string.cd_fab_add_project)
        )
      }

    }

    if (deleteConfirmDialog.value != null) {
      DeleteConfirmationDialog(onConfirmDelete = {
        onDeleteProject(deleteConfirmDialog.value!!)
      }, onDismiss = {
        deleteConfirmDialog.value = null
      })
    }
  }
}

@RootNavGraph(start = true)
@Destination
@Composable
fun ProjectListPage(
  navigator: DestinationsNavigator,
  projectListViewModel: ProjectListViewModel = hiltViewModel(),
  notificationPromptViewModel: NotificationPromptViewModel = hiltViewModel()
) {
  ProjectListPageContent(
    projectList = projectListViewModel.projectListLiveData.observeAsState(initial = emptyList()).value,
    onToggleMute = projectListViewModel::onToggleMute,
    lastSyncedStatus = projectListViewModel.lastSyncedLiveData.observeAsState(initial = null).value,
    onProgressSyncedEvent = projectListViewModel.onProgressSyncFinishEvent.observeAsState(initial = false).value,
    clearOnProgressSyncedEvent = projectListViewModel::clearOnProgressSyncedEvent,
    onPressSync = projectListViewModel::onPressSync,
    onDeleteProject = projectListViewModel::onDeleteProject,
    notificationPromptViewModel = notificationPromptViewModel,
    navigator = navigator
  )
}