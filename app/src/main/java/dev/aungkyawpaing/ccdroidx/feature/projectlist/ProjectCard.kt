package dev.aungkyawpaing.ccdroidx.feature.projectlist

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.OpenInBrowser
import androidx.compose.material.icons.outlined.VolumeOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.google.android.material.composethemeadapter3.Mdc3Theme
import dev.aungkyawpaing.ccdroidx.R
import dev.aungkyawpaing.ccdroidx.data.Authentication
import dev.aungkyawpaing.ccdroidx.data.BuildState
import dev.aungkyawpaing.ccdroidx.data.BuildStatus
import dev.aungkyawpaing.ccdroidx.data.Project
import org.ocpsoft.prettytime.PrettyTime
import java.time.ZonedDateTime


private val prettyTime = PrettyTime()

fun getBuildStatusColor(buildStatus: BuildStatus, buildState: BuildState): Int {
  val buildStatusColor = when (buildState) {
    BuildState.SLEEPING -> {
      when (buildStatus) {
        BuildStatus.SUCCESS -> R.color.build_success
        BuildStatus.FAILURE -> R.color.build_fail
        BuildStatus.EXCEPTION -> R.color.build_fail
        BuildStatus.UNKNOWN -> R.color.build_fail
      }
    }
    BuildState.BUILDING, BuildState.CHECKING_MODIFICATIONS -> {
      R.color.build_in_progress
    }
  }
  return buildStatusColor
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectCard(
  project: Project,
  onOpenRepoClick: ((project: Project) -> Unit),
  onDeleteClick: ((project: Project) -> Unit),
  onToggleMute: ((project: Project) -> Unit)
) {

  var menuExpanded by remember { mutableStateOf(false) }

  Card(modifier = Modifier.fillMaxWidth()) {

    ConstraintLayout(
      modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()
    ) {

      val (buildStatusIndicator, name, lastSyncTime, menu, buildLabel) = createRefs()


      Box(
        modifier = Modifier
          .size(36.dp)
          .clip(CircleShape)
          .border(width = 1.dp, color = MaterialTheme.colorScheme.onSurface, CircleShape)
          .background(
            colorResource(
              id = getBuildStatusColor(
                project.lastBuildStatus,
                project.activity
              )
            )
          )
          .constrainAs(buildStatusIndicator) {
            linkTo(top = name.top, bottom = parent.bottom)
            start.linkTo(parent.start)
          }
      )

      Box(
        modifier = Modifier
          .size(24.dp)
          .constrainAs(menu) {
            top.linkTo(parent.top)
            end.linkTo(parent.end)
          }
      ) {
        IconButton(onClick = {
          menuExpanded = true
        }) {
          Icon(Icons.Default.MoreVert, contentDescription = "More Actions")
        }
        DropdownMenu(
          expanded = menuExpanded,
          onDismissRequest = { menuExpanded = false }
        ) {
          val muteMenuText = if (project.isMuted) {
            stringResource(R.string.action_item_project_unmute)
          } else {
            stringResource(R.string.action_item_project_mute)
          }
          DropdownMenuItem(
            text = { Text(text = muteMenuText) },
            onClick = { onToggleMute(project) },
            leadingIcon = {
              Icon(
                Icons.Outlined.VolumeOff,
                contentDescription = null
              )
            })
          DropdownMenuItem(
            text = { Text(text = stringResource(R.string.action_item_project_open_repo)) },
            onClick = { onOpenRepoClick(project) },
            leadingIcon = {
              Icon(
                Icons.Outlined.OpenInBrowser,
                contentDescription = null
              )
            })
          DropdownMenuItem(
            text = { Text(text = stringResource(R.string.action_item_project_delete_project)) },
            onClick = { onDeleteClick(project) },
            leadingIcon = {
              Icon(
                Icons.Outlined.Delete,
                contentDescription = null
              )
            })
        }
      }

      Text(
        modifier = Modifier
          .padding(horizontal = 8.dp)
          .constrainAs(name)
          {
            linkTo(start = buildStatusIndicator.end, end = menu.start)
            top.linkTo(
              parent.top
            )
            width = Dimension.fillToConstraints
          },
        text = project.name,
        style = MaterialTheme.typography.titleMedium
      )

      Text(
        text = project.lastBuildLabel ?: "",
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier
          .padding(horizontal = 8.dp, vertical = 8.dp)
          .constrainAs(buildLabel)
          {
            end.linkTo(parent.end)
            linkTo(top = menu.bottom, bottom = lastSyncTime.bottom, bias = 1.0f)
          }
      )

      Row(
        modifier = Modifier
          .padding(horizontal = 8.dp, vertical = 8.dp)
          .constrainAs(lastSyncTime)
          {
            linkTo(start = buildStatusIndicator.end, end = buildLabel.start)
            linkTo(top = name.bottom, bottom = parent.bottom)
            width = Dimension.fillToConstraints
          },
      ) {
        Icon(
          Icons.Default.Schedule, contentDescription = null,
          modifier = Modifier
            .size(16.dp)
            .align(Alignment.CenterVertically)
        )
        Text(
          style = MaterialTheme.typography.bodyMedium,
          text = prettyTime.format(project.lastBuildTime),
          modifier = Modifier
            .padding(start = 4.dp)
            .align(Alignment.CenterVertically)
        )
      }
    }

  }
}

@Preview
@Composable
fun ProjectCardPreview() {
  Mdc3Theme {
    ProjectCard(
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
      {},
      {},
      {}
    )
  }
}