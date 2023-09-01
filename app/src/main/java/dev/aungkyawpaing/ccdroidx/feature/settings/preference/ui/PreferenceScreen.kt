package dev.aungkyawpaing.ccdroidx.feature.settings.preference.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.aungkyawpaing.ccdroidx.feature.settings.preference.PreferenceItem

@Composable
fun PreferenceScreen(
  preferenceItems: List<PreferenceItem>,
  modifier: Modifier = Modifier
) {
  LazyColumn(modifier = modifier, userScrollEnabled = false) {
    itemsIndexed(
      items = preferenceItems,
    ) { _ , item ->
      when (item) {
        is PreferenceItem.CategoryItem -> {
          CategoryPreference(categoryPreferenceItem = item)
        }
        is PreferenceItem.SwitchPreferenceItem -> {
          SwitchPreference(switchPreferenceItem = item)
        }
        is PreferenceItem.TextPreferenceItem -> {
          TextPreference(textPreferenceItem = item)
        }
        PreferenceItem.Divider -> {
          Divider()
        }
      }
    }
  }
}