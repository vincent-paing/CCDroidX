package dev.aungkyawpaing.ccdroidx.feature.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import java.time.Duration

val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object Settings {

  val KEY_SYNC_INTERVAL = longPreferencesKey("sync_interval")
  val DEFAULT_SYNC_INTERVAL: Duration = Duration.ofMinutes(15)

  val KEY_OPEN_EXTERNAL_BROWSER = booleanPreferencesKey("open_external_browser")
  const val DEFAULT_OPEN_EXTERNAL_BROWSER = false

}