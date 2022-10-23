package dev.aungkyawpaing.ccdroidx.projectlist

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.*
import dev.aungkyawpaing.ccdroidx.WearApp
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
    val projectList = viewModel.projectLiveData.observeAsState(initial = emptyList())

    ProjectList(
      projects = projectList.value, listState = listState
    )
  }
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
  WearApp {
    ProjectListPage()
  }
}