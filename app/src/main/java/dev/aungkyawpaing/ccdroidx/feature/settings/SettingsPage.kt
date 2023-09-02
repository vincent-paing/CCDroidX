package dev.aungkyawpaing.ccdroidx.feature.settings

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.themeadapter.material3.Mdc3Theme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import dev.aungkyawpaing.ccdroidx.BuildConfig
import dev.aungkyawpaing.ccdroidx.R
import dev.aungkyawpaing.ccdroidx.common.extensions.findActivity
import dev.aungkyawpaing.ccdroidx.feature.browser.openInBrowser
import dev.aungkyawpaing.ccdroidx.feature.settings.preference.PreferenceItem
import dev.aungkyawpaing.ccdroidx.feature.settings.preference.ui.PreferenceScreen
import dev.aungkyawpaing.ccdroidx.feature.settings.syncinterval.SyncIntervalInputDialog


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTopAppBar(
  navigator: DestinationsNavigator
) {
  TopAppBar(
    title = {
      Text(stringResource(id = R.string.settings))
    },
    navigationIcon = {
      IconButton(onClick = {
        navigator.navigateUp()
      }) {
        Icon(Icons.Filled.Close, "Close", tint = MaterialTheme.colorScheme.onPrimary)
      }
    },
    colors = TopAppBarDefaults.topAppBarColors(
      containerColor = MaterialTheme.colorScheme.primary,
      titleContentColor = MaterialTheme.colorScheme.onPrimary,
      actionIconContentColor = MaterialTheme.colorScheme.onPrimary
    )
  )
}

@Destination
@Composable
fun SettingsPage(
  navigator: DestinationsNavigator
) {
  val context = LocalContext.current
  val showSyncIntervalInputDialog = remember { mutableStateOf(false) }

  Scaffold(
    topBar = {
      SettingsTopAppBar(navigator)
    }
  ) { contentPadding ->
    PreferenceScreen(
      preferenceItems = listOf(
        PreferenceItem.CategoryItem(stringResource(id = R.string.sync_category_title)),
        PreferenceItem.TextPreferenceItem(
          title = stringResource(id = R.string.sync_interval_pref_title),
          subtitle = stringResource(id = R.string.sync_interval_pref_summary),
          onClick = {
            showSyncIntervalInputDialog.value = true
          }
        ),
        PreferenceItem.Divider,
        PreferenceItem.CategoryItem(stringResource(id = R.string.others_category_title)),
        PreferenceItem.SwitchPreferenceItem(
          key = "open_external_browser",
          title = stringResource(id = R.string.others_browser_pref_title),
          defaultValue = false
        ),
        PreferenceItem.Divider,
        PreferenceItem.CategoryItem(stringResource(id = R.string.help_category_title)),
        PreferenceItem.TextPreferenceItem(
          title = stringResource(id = R.string.help_feedback_pref_title),
          subtitle = stringResource(id = R.string.sync_interval_pref_summary),
          onClick = {
            context.findActivity()?.let { activityContext ->
              openInBrowser(activityContext, "https://github.com/vincent-paing/CCDroidX/issues")
            }
          }),
        PreferenceItem.TextPreferenceItem(
          title = stringResource(id = R.string.help_version_title),
          subtitle = "${BuildConfig.VERSION_NAME}(${BuildConfig.VERSION_CODE})",
          onClick = {
            /* no-op */
          }),
      ),
      modifier = Modifier.padding(contentPadding)
    )

    if (showSyncIntervalInputDialog.value) {
      SyncIntervalInputDialog(
        onDismissRequest = {
          showSyncIntervalInputDialog.value = false
        }
      )
    }
  }
}

@Preview
@Composable
fun SettingsPagePreview() {
  Mdc3Theme {
    SettingsPage(EmptyDestinationsNavigator)
  }
}