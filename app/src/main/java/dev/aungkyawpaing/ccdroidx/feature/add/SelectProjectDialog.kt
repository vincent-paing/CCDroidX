package dev.aungkyawpaing.ccdroidx.feature.add

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import dev.aungkyawpaing.ccdroidx.R
import dev.aungkyawpaing.ccdroidx.data.Project
import dev.aungkyawpaing.ccdroidx.feature.SelectProjectList

@Composable
fun SelectProjectDialog(
  projectList: List<Project>,
  onProjectSelect: (Project) -> Unit,
  onDismissRequest: () -> Unit
) {

  AlertDialog(
    onDismissRequest = onDismissRequest,
    title = {
      Text(text = stringResource(id = R.string.select_project))
    },
    text = {
      SelectProjectList(projectList = projectList, onProjectSelect = onProjectSelect)
    },
    confirmButton = {},
    dismissButton = {
      TextButton(
        onClick = onDismissRequest,
      ) {
        Text(stringResource(id = android.R.string.cancel))
      }
    }
  )
}