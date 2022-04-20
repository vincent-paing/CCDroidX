package dev.aungkyawpaing.ccdroidx.feature.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aungkyawpaing.ccdroidx.api.FetchProject
import dev.aungkyawpaing.ccdroidx.coroutine.DispatcherProvider
import dev.aungkyawpaing.ccdroidx.data.Project
import dev.aungkyawpaing.ccdroidx.utils.livedata.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddProjectViewModel @Inject constructor(
  private val fetchProject: FetchProject,
  private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

  val projectListLiveEvent = SingleLiveEvent<List<Project>>()

  fun getProjectsFromFeed(feedUrl: String) {
    viewModelScope.launch {
      val projectList = withContext(dispatcherProvider.io()) {
        fetchProject.requestForProjectList(feedUrl)
      }
      projectListLiveEvent.setValue(projectList)
    }
  }
}