package dev.aungkyawpaing.ccdroidx.feature.projectlist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.material.composethemeadapter3.Mdc3Theme

@Composable
fun ProjectListPage(
  viewModel: ProjectListViewModel = viewModel()
) {


  val projectList = viewModel.projectListLiveData.observeAsState(initial = emptyList())

  Mdc3Theme {
    ProjectList(projectList.value)
  }
  
}