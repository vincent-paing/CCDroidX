package dev.aungkyawpaing.ccdroidx.projectlist.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.*
import dev.aungkyawpaing.ccdroidx.R
import dev.aungkyawpaing.wear.datalayer.MiniProject

@Composable
fun ProjectList(
  projects: List<MiniProject>,
  listState: ScalingLazyListState,
) {
  ScalingLazyColumn(contentPadding = PaddingValues(top = 40.dp), state = listState, content = {
    item {
      Text(
        text = stringResource(id = R.string.project_list_header),
        style = MaterialTheme.typography.title1
      )
    }
    items(
      items = projects,
    ) { project ->
      ProjectChip(project = project)
    }
  })
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun ProjectListPreview() {
  ProjectList(
    listOf(
      MiniProject("some-project-one", true),
      MiniProject("some-project-two", false),
      MiniProject("some-project-three", true)
    ), rememberScalingLazyListState()
  )
}