package dev.aungkyawpaing.ccdroidx.feature.settings.preference.ui

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
fun CategoryPreference(
  categoryPreferenceItem: PreferenceItem.CategoryItem,
  modifier: Modifier = Modifier,
) {
  val (title) = categoryPreferenceItem

  Column(
    modifier = modifier
      .fillMaxWidth()
  ) {
    Text(
      text = title,
      style = MaterialTheme.typography.titleSmall,
      color = MaterialTheme.colorScheme.primary,
      modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp)
    )
  }
}

@Preview
@Composable
fun CategoryPreferencePreview() {
  Mdc3Theme {
    Surface {
      CategoryPreference(
        PreferenceItem.CategoryItem(
          title = "Some Category",
        )
      )
    }
  }
}
