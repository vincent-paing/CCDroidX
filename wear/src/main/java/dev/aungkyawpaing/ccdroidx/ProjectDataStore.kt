package dev.aungkyawpaing.ccdroidx

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProjectDataStore @Inject constructor(
  @ApplicationContext private val context: Context
) {

  private val Context.prefDataStore: DataStore<Preferences> by preferencesDataStore(name = "pref")
  private val FAILING_PROJECT_COUNT_KEY = longPreferencesKey("fail_project_count")

  suspend fun putFailingProjectCount(failingProjectCount: Long) {
    context.prefDataStore.edit {
      it[FAILING_PROJECT_COUNT_KEY] = failingProjectCount
    }
  }

  fun getFailingProjectCount(): Flow<Long> {
    return context.prefDataStore.data.map {
      it[FAILING_PROJECT_COUNT_KEY] ?: 0
    }
  }

}