package dev.aungkyawpaing.ccdroidx.feature.projectlist.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dev.aungkyawpaing.ccdroidx.R
import dev.aungkyawpaing.ccdroidx.feature.destinations.SettingsPageDestination
import dev.aungkyawpaing.ccdroidx.feature.sync.LastSyncedState
import dev.aungkyawpaing.ccdroidx.feature.sync.LastSyncedStatus
import org.ocpsoft.prettytime.PrettyTime
import java.time.Clock

@Composable
private fun getSubtitleText(lastSyncedStatus: LastSyncedStatus?, clock: Clock): String =
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
          PrettyTime(clock.instant()).format(lastSyncedStatus.lastSyncedDateTime)
        )
      }
    }
  }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectListTopAppBar(
  lastSyncedStatus: LastSyncedStatus?,
  onProgressSyncedEvent: Boolean,
  onPressSync: () -> Unit,
  clearOnProgressSyncedEvent: () -> Unit,
  navigator: DestinationsNavigator,
  clock: Clock
) {
  TopAppBar(
    title = {
      Column {
        Text(stringResource(id = R.string.app_name), modifier = Modifier.semantics {
          contentDescription = "CC Droid X"
        })
        Text(
          text = getSubtitleText(lastSyncedStatus, clock),
          style = MaterialTheme.typography.bodyMedium
        )
      }
    },
    actions = {
      IconButton(
        onClick = onPressSync,
        modifier = Modifier.semantics {
          stateDescription = if (onProgressSyncedEvent) "Finished syncing" else ""

          if (onProgressSyncedEvent) {
            clearOnProgressSyncedEvent()
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