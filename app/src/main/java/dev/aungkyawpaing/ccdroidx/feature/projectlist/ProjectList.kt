package dev.aungkyawpaing.ccdroidx.feature.projectlist

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.aungkyawpaing.ccdroidx.data.Project

@Composable
fun ProjectList(
  projectList: List<Project>
) {

  LazyColumn(
    contentPadding = PaddingValues(8.dp),
    modifier = Modifier.padding(bottom = 56.dp)
  ) {
    itemsIndexed(
      items = projectList,
      key = { _, project ->
        project.id
      }
    ) { index, project ->
      ProjectCard(project = project)
    }
  }
}