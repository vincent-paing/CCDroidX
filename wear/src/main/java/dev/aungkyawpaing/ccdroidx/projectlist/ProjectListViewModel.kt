package dev.aungkyawpaing.ccdroidx.projectlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aungkyawpaing.ccdroidx.ProjectDataStore
import javax.inject.Inject

@HiltViewModel
class ProjectListViewModel @Inject constructor(
  projectDataStore: ProjectDataStore
) : ViewModel() {
  val projectLiveData = projectDataStore.getProjectList().asLiveData()
}