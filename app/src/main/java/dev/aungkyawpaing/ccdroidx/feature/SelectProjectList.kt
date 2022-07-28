package dev.aungkyawpaing.ccdroidx.feature

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.material.composethemeadapter3.Mdc3Theme
import dev.aungkyawpaing.ccdroidx.data.Authentication
import dev.aungkyawpaing.ccdroidx.data.BuildState
import dev.aungkyawpaing.ccdroidx.data.BuildStatus
import dev.aungkyawpaing.ccdroidx.data.Project
import java.time.ZonedDateTime

@Composable
fun SelectProjectList(
  projectList: List<Project>,
  onProjectSelect: (Project) -> Unit,
) {
  LazyColumn {
    itemsIndexed(
      items = projectList,
      key = { _, project ->
        project.id
      }
    ) { _, project ->

      Box(
        modifier = Modifier
          .clickable {
            onProjectSelect(project)
          }
          .padding(16.dp)
          .fillMaxWidth()
      ) {
        Text(
          text = project.name
        )
      }
    }
  }
}

@Preview
@Composable
fun SelectProjectListPreview() {
  Mdc3Theme {
    SelectProjectList(
      projectList = listOf(
        Project(
          id = 0L,
          name = "vincent-paing/ccdroidx with very long name here!",
          activity = BuildState.SLEEPING,
          lastBuildStatus = BuildStatus.SUCCESS,
          lastBuildLabel = "1234",
          lastBuildTime = ZonedDateTime.now(),
          nextBuildTime = null,
          webUrl = "https://www.example.com",
          feedUrl = "https://www.example.com/cc.xml",
          isMuted = false,
          mutedUntil = null,
          authentication = Authentication(
            username = "username",
            password = "password"
          )
        ),
        Project(
          id = 1L,
          name = "vincent-paing/ccdroidx",
          activity = BuildState.SLEEPING,
          lastBuildStatus = BuildStatus.SUCCESS,
          lastBuildLabel = "1234",
          lastBuildTime = ZonedDateTime.now(),
          nextBuildTime = null,
          webUrl = "https://www.example.com",
          feedUrl = "https://www.example.com/cc.xml",
          isMuted = false,
          mutedUntil = null,
          authentication = Authentication(
            username = "username",
            password = "password"
          )
        ),
      ),
      onProjectSelect = {}
    )
  }
}