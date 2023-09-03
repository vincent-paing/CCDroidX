package dev.aungkyawpaing.ccdroidx.feature.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import java.time.Duration

val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object Settings {

  val DEFAULT_SYNC_INTERVAL: Duration = Duration.ofMinutes(15)
  val KEY_SYNC_INTERVAL = longPreferencesKey("sync_interval")

}