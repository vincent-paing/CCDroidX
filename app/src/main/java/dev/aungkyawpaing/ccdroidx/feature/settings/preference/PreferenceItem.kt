package dev.aungkyawpaing.ccdroidx.feature.settings.preference

sealed class PreferenceItem {
  data class CategoryItem(
    val title: String
  ) : PreferenceItem()

  data class TextPreferenceItem(
    val title: String,
    val subtitle: String? = null,
    val onClick: () -> Unit
  ) : PreferenceItem()

  data class SwitchPreferenceItem(
    val key: String,
    val title: String,
    val defaultValue: Boolean,
    val subtitle: String? = null
  ) : PreferenceItem()

  object Divider : PreferenceItem()

}