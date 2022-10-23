package dev.aungkyawpaing.ccdroidx.feature.projectlist.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.OpenInBrowser
import androidx.compose.material.icons.outlined.VolumeOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.google.android.material.composethemeadapter3.Mdc3Theme
import dev.aungkyawpaing.ccdroidx.R
import dev.aungkyawpaing.ccdroidx.common.Authentication
import dev.aungkyawpaing.ccdroidx.common.BuildState
import dev.aungkyawpaing.ccdroidx.common.BuildStatus
import dev.aungkyawpaing.ccdroidx.common.Project
import org.ocpsoft.prettytime.PrettyTime
import java.time.ZonedDateTime


private val prettyTime = PrettyTime()

@Composable
fun ProjectNameText(
  projectName: String, modifier: Modifier
) {
  Text(
    modifier = modifier, text = projectName, style = MaterialTheme.typography.titleMedium
  )
}


enum class BuildStatusIndicatorStyle {
  IN_PROGRESS, FAILED, SUCCESS
}

fun getBuildStatusIndicatorStyle(
  buildStatus: BuildStatus, buildState: BuildState
): BuildStatusIndicatorStyle {
  return when (buildState) {
    BuildState.SLEEPING -> {
      when (buildStatus) {
        BuildStatus.SUCCESS -> BuildStatusIndicatorStyle.SUCCESS
        BuildStatus.FAILURE -> BuildStatusIndicatorStyle.FAILED
        BuildStatus.EXCEPTION -> BuildStatusIndicatorStyle.FAILED
        BuildStatus.UNKNOWN -> BuildStatusIndicatorStyle.FAILED
      }
    }
    BuildState.BUILDING, BuildState.CHECKING_MODIFICATIONS -> {
      BuildStatusIndicatorStyle.IN_PROGRESS
    }
  }
}


fun getBuildStatusColor(buildStatusIndicatorStyle: BuildStatusIndicatorStyle): Int {
  return when (buildStatusIndicatorStyle) {
    BuildStatusIndicatorStyle.IN_PROGRESS -> R.color.build_in_progress
    BuildStatusIndicatorStyle.FAILED -> R.color.build_fail
    BuildStatusIndicatorStyle.SUCCESS -> R.color.build_success
  }
}

fun getBuildStatusIndicator(buildStatusIndicatorStyle: BuildStatusIndicatorStyle): ImageVector {
  return when (buildStatusIndicatorStyle) {
    BuildStatusIndicatorStyle.IN_PROGRESS -> Icons.Filled.Autorenew
    BuildStatusIndicatorStyle.FAILED -> Icons.Filled.Close
    BuildStatusIndicatorStyle.SUCCESS -> Icons.Filled.Done
  }
}


@Composable
fun ProjectBuildStatusIcon(
  project: Project, modifier: Modifier
) {

  val buildStatusIndicatorStyle = getBuildStatusIndicatorStyle(
    project.lastBuildStatus, project.activity
  )

  Box(modifier = modifier
    .size(36.dp)
    .clip(CircleShape)
    .border(width = 1.dp, color = MaterialTheme.colorScheme.onSurface, CircleShape)
    .background(
      colorResource(
        id = getBuildStatusColor(buildStatusIndicatorStyle)
      )
    )
    .semantics {
      contentDescription = "Status: ${project.lastBuildStatus}"
    }) {
    Icon(
      getBuildStatusIndicator(buildStatusIndicatorStyle),
      contentDescription = null,
      tint = MaterialTheme.colorScheme.surfaceVariant,
      modifier = Modifier
        .size(24.dp)
        .align(Alignment.Center)
    )
  }
}

@Composable
fun ProjectLastBuildText(
  project: Project, modifier: Modifier
) {
  Text(text = project.lastBuildLabel ?: "",
    style = MaterialTheme.typography.bodyMedium,
    modifier = modifier
      .padding(horizontal = 8.dp, vertical = 8.dp)
      .semantics {
        contentDescription = "Build Label : ${project.lastBuildLabel}."
      })
}

@Composable
fun ProjectLastBuildAgoText(
  project: Project, modifier: Modifier
) {
  Row(
    modifier = modifier.padding(horizontal = 8.dp, vertical = 8.dp)
  ) {
    Icon(
      Icons.Default.Schedule,
      contentDescription = null,
      modifier = Modifier
        .size(16.dp)
        .align(Alignment.CenterVertically)
    )
    val lastBuildAgo = prettyTime.format(project.lastBuildTime)
    Text(style = MaterialTheme.typography.bodyMedium,
      text = lastBuildAgo,
      modifier = Modifier
        .padding(start = 4.dp)
        .align(Alignment.CenterVertically)
        .semantics {
          contentDescription = "Last built: $lastBuildAgo}"
        })
  }
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

  val onExpandMenu = {
    menuExpanded = true
  }

  Card(modifier = Modifier
    .fillMaxWidth()
    .semantics(mergeDescendants = true) {
      onClick(label = "More Action") {
        onExpandMenu()
        true
      }
    }) {

    ConstraintLayout(
      modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()
    ) {

      val (buildStatusIndicator, name, lastSyncTime, menu, buildLabel) = createRefs()

      //More action
      Box(modifier = Modifier
        .size(24.dp)
        .constrainAs(menu) {
          top.linkTo(parent.top)
          end.linkTo(parent.end)
        }) {
        IconButton(onClick = onExpandMenu, modifier = Modifier.clearAndSetSemantics { }) {
          Icon(Icons.Default.MoreVert, contentDescription = null)
        }
        DropdownMenu(expanded = menuExpanded, onDismissRequest = { menuExpanded = false }) {
          val muteMenuText = if (project.isMuted) {
            stringResource(R.string.action_item_project_unmute)
          } else {
            stringResource(R.string.action_item_project_mute)
          }
          DropdownMenuItem(text = { Text(text = muteMenuText) },
            onClick = { onToggleMute(project) },
            leadingIcon = {
              Icon(
                Icons.Outlined.VolumeOff, contentDescription = null
              )
            })
          DropdownMenuItem(text = { Text(text = stringResource(R.string.action_item_project_open_repo)) },
            onClick = { onOpenRepoClick(project) },
            leadingIcon = {
              Icon(
                Icons.Outlined.OpenInBrowser, contentDescription = null
              )
            })
          DropdownMenuItem(text = { Text(text = stringResource(R.string.action_item_project_delete_project)) },
            onClick = { onDeleteClick(project) },
            leadingIcon = {
              Icon(
                Icons.Outlined.Delete, contentDescription = null
              )
            })
        }
      }

      ProjectNameText(
        projectName = project.name,
        modifier = Modifier
          .padding(horizontal = 8.dp)
          .constrainAs(name) {
            linkTo(start = buildStatusIndicator.end, end = menu.start)
            top.linkTo(
              parent.top
            )
            width = Dimension.fillToConstraints
          },
      )

      //Build status icon
      ProjectBuildStatusIcon(
        project = project,
        modifier = Modifier.constrainAs(buildStatusIndicator) {
          linkTo(top = name.top, bottom = parent.bottom)
          start.linkTo(parent.start)
        })

      //Last build label
      ProjectLastBuildText(project = project, modifier = Modifier.constrainAs(buildLabel) {
        end.linkTo(parent.end)
        linkTo(top = menu.bottom, bottom = lastSyncTime.bottom, bias = 1.0f)
      })

      ProjectLastBuildAgoText(
        project = project,
        modifier = Modifier.constrainAs(lastSyncTime) {
          linkTo(start = buildStatusIndicator.end, end = buildLabel.start)
          linkTo(top = name.bottom, bottom = parent.bottom)
          width = Dimension.fillToConstraints
        },
      )
    }

  }
}

@Preview
@Composable
fun ProjectCardPreviewSuccess() {
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
          username = "username", password = "password"
        )
      ), {}, {}, {})
  }
}

@Preview
@Composable
fun ProjectCardPreviewFailed() {
  Mdc3Theme {
    ProjectCard(
      Project(
        id = 0L,
        name = "vincent-paing/ccdroidx with very long name here!",
        activity = BuildState.SLEEPING,
        lastBuildStatus = BuildStatus.FAILURE,
        lastBuildLabel = "1234",
        lastBuildTime = ZonedDateTime.now(),
        nextBuildTime = null,
        webUrl = "https://www.example.com",
        feedUrl = "https://www.example.com/cc.xml",
        isMuted = false,
        mutedUntil = null,
        authentication = Authentication(
          username = "username", password = "password"
        )
      ), {}, {}, {})
  }
}

@Preview
@Composable
fun ProjectCardPreviewInProgress() {
  Mdc3Theme {
    ProjectCard(
      Project(
        id = 0L,
        name = "vincent-paing/ccdroidx with very long name here!",
        activity = BuildState.BUILDING,
        lastBuildStatus = BuildStatus.SUCCESS,
        lastBuildLabel = "1234",
        lastBuildTime = ZonedDateTime.now(),
        nextBuildTime = null,
        webUrl = "https://www.example.com",
        feedUrl = "https://www.example.com/cc.xml",
        isMuted = false,
        mutedUntil = null,
        authentication = Authentication(
          username = "username", password = "password"
        )
      ), {}, {}, {})
  }
}