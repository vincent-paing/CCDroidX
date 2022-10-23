package dev.aungkyawpaing.ccdroidx.projectlist.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.*
import dev.aungkyawpaing.wear.datalayer.MiniProject

private val buildFailColor = Color(0xFFB3261E)
private val buildSuccessColor = Color(0xFF4BB543)

@Composable
fun ProjectChip(
  project: MiniProject
) {
  Chip(
    modifier = Modifier.fillMaxWidth(),
    label = {
      Text(text = project.name)
    },
    icon = {
      if (project.isSuccess) {
        Icon(Icons.Filled.Done, contentDescription = null)
      } else {
        Icon(Icons.Filled.Close, contentDescription = null)
      }
    },
    colors = ChipDefaults.primaryChipColors(
      backgroundColor = if (project.isSuccess) buildSuccessColor else buildFailColor
    ),
    onClick = { /*TODO*/ })
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun ProjectChipSuccessPreview() {
  ProjectChip(project = MiniProject("project", true))
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun ProjectChipFailurePreview() {
  ProjectChip(project = MiniProject("project with a very very very long long name", false))
}