package dev.aungkyawpaing.ccdroidx.projectlist

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.*
import dev.aungkyawpaing.ccdroidx.projectlist.component.EmptyProjectError
import dev.aungkyawpaing.ccdroidx.projectlist.component.ProjectList

@Composable
fun ProjectListPage(
  viewModel: ProjectListViewModel = viewModel()
) {

  val listState = rememberScalingLazyListState()

  Scaffold(
    modifier = Modifier
      .background(MaterialTheme.colors.background),
    positionIndicator = {
      PositionIndicator(
        scalingLazyListState = listState,
      )
    },
    timeText = {
      if (!listState.isScrollInProgress) {
        TimeText()
      }
    },
    vignette = {
      Vignette(vignettePosition = VignettePosition.TopAndBottom)
    },

    ) {
    val projectList = viewModel.projectLiveData.observeAsState(initial = emptyList()).value

    if (projectList.isEmpty()) {
      EmptyProjectError()
    } else {
      ProjectList(
        projects = projectList, listState = listState
      )
    }
  }
}