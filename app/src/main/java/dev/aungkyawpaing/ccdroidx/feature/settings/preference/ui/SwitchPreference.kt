package dev.aungkyawpaing.ccdroidx.feature.settings.preference.ui

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.google.accompanist.themeadapter.material3.Mdc3Theme
import dev.aungkyawpaing.ccdroidx.feature.settings.preference.PreferenceItem
import dev.aungkyawpaing.ccdroidx.feature.settings.settingsDataStore
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

private fun Context.switchData(key: Preferences.Key<Boolean>, defaultValue: Boolean) =
  settingsDataStore.data.map { preferences ->
    preferences[key] ?: defaultValue
  }

@Composable
fun SwitchPreference(
  switchPreferenceItem: PreferenceItem.SwitchPreferenceItem,
  modifier: Modifier = Modifier,
) {
  val dataStore = LocalContext.current.settingsDataStore
  val (key, title, defaultValue, subtitle) = switchPreferenceItem
  val value =
    LocalContext.current.switchData(key, defaultValue).collectAsState(initial = null).value
  val scope = rememberCoroutineScope()

  val onCheckedChange = {
    scope.launch {
      if (value != null) {
        dataStore.edit {
          it[key] = !value
        }
      }
    }
  }

  Row(
    modifier = modifier
      .fillMaxWidth()
      .clickable(onClickLabel = "Toggle") {
        onCheckedChange()
      }
      .padding(horizontal = 32.dp, vertical = 16.dp)
  ) {
    Column(
      modifier = Modifier
        .weight(1.0F)
        .align(Alignment.CenterVertically)
    ) {
      Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurface,
      )
      if (subtitle != null) {
        Text(
          text = subtitle,
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }
    if (value != null) {
      Switch(checked = value, onCheckedChange = {
        onCheckedChange()
      })
    }
  }
}

@Preview
@Composable
fun SwitchPreferencePreviewTitleOnly() {
  Mdc3Theme {
    Surface {
      SwitchPreference(
        PreferenceItem.SwitchPreferenceItem(
          key = booleanPreferencesKey("preview"),
          title = "Some title",
          defaultValue = false
        )
      )
    }
  }
}

@Preview
@Composable
fun SwitchPreferencePreviewTitleAndSubtitleOnly() {
  Mdc3Theme {
    Surface {
      SwitchPreference(
        PreferenceItem.SwitchPreferenceItem(
          key = booleanPreferencesKey("preview"),
          title = "Some title",
          subtitle = "Some subtitle",
          defaultValue = true
        )
      )
    }
  }
}