package dev.aungkyawpaing.ccdroidx.feature.settings.preference.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.themeadapter.material3.Mdc3Theme
import dev.aungkyawpaing.ccdroidx.feature.settings.preference.PreferenceItem

@Composable
fun TextPreference(
  textPreferenceItem: PreferenceItem.TextPreferenceItem,
  modifier: Modifier = Modifier,
) {
  val (title, subtitle, onClick) = textPreferenceItem

  Column(modifier = modifier
    .fillMaxWidth()
    .clickable {
      onClick()
    }
    .padding(horizontal = 32.dp, vertical = 16.dp))
  {
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
}

@Preview
@Composable
fun TextPreferencePreviewTitleOnly() {
  Mdc3Theme {
    Surface {
      TextPreference(
        PreferenceItem.TextPreferenceItem(
          title = "Links open externally",
          onClick = {})
      )
    }
  }
}

@Preview
@Composable
fun TextPreferencePreviewTitleAndSubtitle() {
  Mdc3Theme {
    Surface {
      TextPreference(
        PreferenceItem.TextPreferenceItem(
          title = "Sync Interval",
          subtitle = "How often ping server",
          onClick = {})
      )
    }
  }
}