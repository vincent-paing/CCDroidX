package dev.aungkyawpaing.ccdroidx.feature.projectlist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.material.composethemeadapter3.Mdc3Theme
import dev.aungkyawpaing.ccdroidx.feature.browser.openInBrowser
import dev.aungkyawpaing.ccdroidx.utils.extensions.findActivity

@Composable
fun ProjectListPage(
  viewModel: ProjectListViewModel = viewModel()
) {

  val context = LocalContext.current
  val projectList = viewModel.projectListLiveData.observeAsState(initial = emptyList())

  Mdc3Theme {
    ProjectList(
      projectList = projectList.value,
      onOpenRepoClick = { project ->
        context.findActivity()?.let { activity ->
          openInBrowser(activity, project.webUrl)
        }
      },
      onDeleteClick = viewModel::onDeleteProject,
      onToggleMute = viewModel::onToggleMute
    )
  }
}