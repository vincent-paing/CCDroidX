package dev.aungkyawpaing.ccdroidx

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.aungkyawpaing.wear.datalayer.MiniProject
import dev.aungkyawpaing.wear.datalayer.MiniProjectSerializer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProjectDataStore @Inject constructor(
  @ApplicationContext private val context: Context,
  private val miniProjectSerializer: MiniProjectSerializer
) {

  private val Context.prefDataStore: DataStore<Preferences> by preferencesDataStore(name = "pref")
  private val PROJECTS_KEY = stringPreferencesKey("projects")

  suspend fun putProjectList(projectList: List<MiniProject>) {
    context.prefDataStore.edit {
      it[PROJECTS_KEY] = miniProjectSerializer.serializeMiniProjects(projectList)
    }
  }

  fun getProjectList(): Flow<List<MiniProject>> {
    return context.prefDataStore.data.map { pref ->
      val jsonString = pref[PROJECTS_KEY] ?: return@map emptyList()
      miniProjectSerializer.deserializeProjects(jsonString)
    }
  }

  fun getFailingProjectCount(): Flow<Int> {
    return context.prefDataStore.data.map { pref ->
      val jsonString = pref[PROJECTS_KEY] ?: return@map 0
      miniProjectSerializer.deserializeProjects(jsonString).count { project ->
        !project.isSuccess
      }
    }
  }


}