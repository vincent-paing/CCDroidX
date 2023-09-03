package dev.aungkyawpaing.ccdroidx.feature

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aungkyawpaing.ccdroidx.data.ProjectRepo
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  private val projectRepo: ProjectRepo
) : ViewModel() {

  suspend fun getProjectUrlById(projectId: Long): String? {
    return projectRepo.getById(projectId).firstOrNull()?.webUrl
  }

}