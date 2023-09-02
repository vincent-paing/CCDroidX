package dev.aungkyawpaing.ccdroidx.feature.projectlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
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
import dev.aungkyawpaing.ccdroidx.feature.add.AddProjectDialog
import dev.aungkyawpaing.ccdroidx.feature.browser.openInBrowser
import dev.aungkyawpaing.ccdroidx.feature.destinations.SettingsPageDestination
import dev.aungkyawpaing.ccdroidx.feature.notification.prompt.NotificationPrompt
import dev.aungkyawpaing.ccdroidx.feature.projectlist.component.ProjectList
import dev.aungkyawpaing.ccdroidx.feature.sync.LastSyncedState
import dev.aungkyawpaing.ccdroidx.feature.sync.LastSyncedStatus
import org.ocpsoft.prettytime.PrettyTime

@Composable
private fun getSubtitleText(lastSyncedStatus: LastSyncedStatus?): String =
  if (lastSyncedStatus == null) {
    stringResource(R.string.welcome)
  } else {
    when (lastSyncedStatus.lastSyncedState) {
      LastSyncedState.SYNCING -> {
        stringResource(R.string.syncing)
      }

      LastSyncedState.SUCCESS, LastSyncedState.FAILED -> {
        stringResource(
          R.string.last_synced_x,
          PrettyTime().format(lastSyncedStatus.lastSyncedDateTime)
        )
      }
    }
  }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectListTopAppBar(
  viewModel: ProjectListViewModel,
  navigator: DestinationsNavigator
) {
  val lastSynced = viewModel.lastSyncedLiveData.observeAsState(initial = null)
  val onProgressSyncedEvent = viewModel.onProgressSyncFinishEvent.observeAsState(initial = false)

  TopAppBar(
    title = {
      Column {
        Text(stringResource(id = R.string.app_name), modifier = Modifier.semantics {
          contentDescription = "CC Droid X"
        })
        Text(
          text = getSubtitleText(lastSynced.value),
          style = MaterialTheme.typography.bodyMedium
        )
      }
    },
    actions = {
      IconButton(
        onClick = viewModel::onPressSync,
        modifier = Modifier.semantics {
          stateDescription = if (onProgressSyncedEvent.value) "Finished syncing" else ""

          if (onProgressSyncedEvent.value) {
            viewModel.clearOnProgressSyncedEvent()
          }
        }
      ) {
        Icon(
          Icons.Filled.Sync,
          contentDescription = stringResource(R.string.menu_item_sync_project_status)
        )
      }

      IconButton(onClick = {
        navigator.navigate(SettingsPageDestination())
      }) {
        Icon(
          Icons.Filled.Settings,
          contentDescription = stringResource(R.string.menu_item_settings)
        )
      }
    },
    colors = TopAppBarDefaults.topAppBarColors(
      containerColor = MaterialTheme.colorScheme.primary,
      titleContentColor = MaterialTheme.colorScheme.onPrimary,
      actionIconContentColor = MaterialTheme.colorScheme.onPrimary
    )
  )
}

@Composable
fun DeleteConfirmationDialog(
  onConfirmDelete: () -> Unit,
  onDismiss: () -> Unit
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Text(text = stringResource(id = R.string.confirm_delete_title))
    },
    text = {
      Text(text = stringResource(id = R.string.confirm_delete_message))
    },
    confirmButton = {
      TextButton(
        onClick = {
          onConfirmDelete()
          onDismiss()
        }
      ) {
        Text(stringResource(id = R.string.action_item_project_delete_project))
      }
    },
    dismissButton = {
      TextButton(
        onClick = onDismiss
      ) {
        Text(stringResource(id = android.R.string.cancel))
      }
    }
  )
}

@RootNavGraph(start = true)
@Destination
@Composable
fun ProjectListPage(
  navigator: DestinationsNavigator,
  viewModel: ProjectListViewModel = hiltViewModel()
) {

  val context = LocalContext.current
  val addProjectDialog = remember { mutableStateOf(false) }

  Scaffold(
    topBar = {
      ProjectListTopAppBar(viewModel, navigator)
    }
  ) { contentPadding ->
    val projectList = viewModel.projectListLiveData.observeAsState(initial = emptyList())
    val deleteConfirmDialog =
      remember { mutableStateOf<Project?>(null) }

    ConstraintLayout(
      modifier = Modifier
        .padding(contentPadding)
        .fillMaxSize()
    ) {

      val (notificationPrompt, projectListComponent, fabAddProject) = createRefs()

      ProjectList(
        projectList = projectList.value,
        onOpenRepoClick = { project ->
          context.findActivity()?.let { activity ->
            openInBrowser(activity, project.webUrl)
          }
        },
        onDeleteClick = { project ->
          deleteConfirmDialog.value = project
        },
        onToggleMute = viewModel::onToggleMute,
        modifier = Modifier.constrainAs(projectListComponent) {
          end.linkTo(parent.end)
          start.linkTo(parent.start)
          bottom.linkTo(notificationPrompt.top)
          top.linkTo(parent.top)
          height = Dimension.fillToConstraints
          width = Dimension.fillToConstraints
        }
      )

      NotificationPrompt(modifier = Modifier.constrainAs(notificationPrompt) {
        end.linkTo(parent.end)
        start.linkTo(parent.start)
        bottom.linkTo(parent.bottom)
      })

      FloatingActionButton(onClick = {
        addProjectDialog.value = true
      }, modifier = Modifier
        .constrainAs(fabAddProject) {
          end.linkTo(parent.end)
          bottom.linkTo(notificationPrompt.top)
        }
        .padding(16.dp)) {
        Icon(
          Icons.Filled.Add,
          contentDescription = stringResource(R.string.cd_fab_add_project)
        )
      }

    }

    if (deleteConfirmDialog.value != null) {
      DeleteConfirmationDialog(
        onConfirmDelete = {
          viewModel.onDeleteProject(deleteConfirmDialog.value!!)
        },
        onDismiss = {
          deleteConfirmDialog.value = null
        }
      )
    }

    if (addProjectDialog.value) {
      AddProjectDialog(
        onDismissRequest = {
          addProjectDialog.value = false
        }
      )
    }
  }
}