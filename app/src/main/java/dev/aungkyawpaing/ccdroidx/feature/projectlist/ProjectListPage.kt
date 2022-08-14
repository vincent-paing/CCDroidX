package dev.aungkyawpaing.ccdroidx.feature.projectlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.material.composethemeadapter3.Mdc3Theme
import dev.aungkyawpaing.ccdroidx.R
import dev.aungkyawpaing.ccdroidx.data.Project
import dev.aungkyawpaing.ccdroidx.feature.add.AddProjectDialog
import dev.aungkyawpaing.ccdroidx.feature.browser.openInBrowser
import dev.aungkyawpaing.ccdroidx.feature.sync.LastSyncedState
import dev.aungkyawpaing.ccdroidx.feature.sync.LastSyncedStatus
import dev.aungkyawpaing.ccdroidx.utils.extensions.findActivity
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

@Composable
fun ProjectListTopAppBar(
  lastSynced: LastSyncedStatus?,
  onPressSync: () -> Unit,
  onClickSettings: () -> Unit
) {
  SmallTopAppBar(
    title = {
      Column {
        Text(stringResource(id = R.string.app_name), modifier = Modifier.semantics {
          contentDescription = "CC Droid X"
        })
        Text(
          text = getSubtitleText(lastSynced),
          style = MaterialTheme.typography.bodyMedium
        )
      }
    },
    actions = {
      IconButton(onClick = onPressSync) {
        Icon(
          Icons.Filled.Sync,
          contentDescription = stringResource(R.string.menu_item_sync_project_status)
        )
      }
      IconButton(onClick = onClickSettings) {
        Icon(
          Icons.Filled.Settings,
          contentDescription = stringResource(R.string.menu_item_settings)
        )
      }
    },
    colors = TopAppBarDefaults.smallTopAppBarColors(
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectListPage(
  viewModel: ProjectListViewModel = viewModel(),
  onClickSettings: (() -> Unit),
) {

  val context = LocalContext.current
  val addProjectDialog = remember { mutableStateOf(false) }

  Mdc3Theme {
    Scaffold(
      topBar = {
        val lastSynced = viewModel.lastSyncedLiveData.observeAsState(initial = null)
        ProjectListTopAppBar(lastSynced.value, viewModel::onPressSync, onClickSettings)
      },
      floatingActionButtonPosition = FabPosition.End,
      floatingActionButton = {
        FloatingActionButton(onClick = {
          addProjectDialog.value = true
        }) {
          Icon(
            Icons.Filled.Add,
            contentDescription = stringResource(R.string.cd_fab_add_project)
          )
        }
      }
    ) { contentPadding ->
      val projectList = viewModel.projectListLiveData.observeAsState(initial = emptyList())
      val deleteConfirmDialog = remember { mutableStateOf<Project?>(null) }

      Box(modifier = Modifier.padding(contentPadding)) {
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
          onToggleMute = viewModel::onToggleMute
        )
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
}