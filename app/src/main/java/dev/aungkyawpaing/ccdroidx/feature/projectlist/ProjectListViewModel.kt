package dev.aungkyawpaing.ccdroidx.feature.projectlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aungkyawpaing.ccdroidx.api.FetchProject
import dev.aungkyawpaing.ccdroidx.coroutine.DispatcherProvider
import dev.aungkyawpaing.ccdroidx.data.Project
import dev.aungkyawpaing.ccdroidx.data.ProjectRepo
import dev.aungkyawpaing.ccdroidx.utils.livedata.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProjectListViewModel @Inject constructor(
  private val projectRepo: ProjectRepo,
  private val dispatcherProvider: DispatcherProvider,
) : ViewModel() {

  val projectListLiveData = projectRepo.getAll().asLiveData()

}