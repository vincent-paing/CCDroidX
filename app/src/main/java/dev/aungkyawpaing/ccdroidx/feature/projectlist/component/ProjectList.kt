package dev.aungkyawpaing.ccdroidx.feature.projectlist.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.aungkyawpaing.ccdroidx.common.Project

@Composable
fun ProjectList(
  projectList: List<Project>,
  onOpenRepoClick: ((project: Project) -> Unit),
  onDeleteClick: ((project: Project) -> Unit),
  onToggleMute: ((project: Project) -> Unit),
  modifier: Modifier = Modifier
) {

  LazyColumn(
    contentPadding = PaddingValues(8.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp),
    modifier = modifier.padding(bottom = 56.dp)
  ) {
    itemsIndexed(
      items = projectList,
      key = { _, project ->
        project.id
      }
    ) { _, project ->
      ProjectCard(
        project = project,
        onOpenRepoClick = onOpenRepoClick,
        onDeleteClick = onDeleteClick,
        onToggleMute = onToggleMute
      )
    }
  }
}