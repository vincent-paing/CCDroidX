package dev.aungkyawpaing.ccdroidx.feature.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.glance.ColorFilter
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionParametersOf
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.semantics.semantics
import androidx.glance.semantics.testTag
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import dagger.hilt.android.AndroidEntryPoint
import dev.aungkyawpaing.ccdroidx.R
import dev.aungkyawpaing.ccdroidx.common.BuildStatus
import dev.aungkyawpaing.ccdroidx.common.Project
import dev.aungkyawpaing.ccdroidx.data.ProjectRepo
import dev.aungkyawpaing.ccdroidx.feature.MainActivity
import dev.aungkyawpaing.ccdroidx.feature.sync.SyncWorkerScheduler
import javax.inject.Inject

class DashboardWidget(
  private val projectRepo: ProjectRepo
) : GlanceAppWidget() {

  override val sizeMode = SizeMode.Exact
  override suspend fun provideGlance(context: Context, id: GlanceId) {

    provideContent {
      val failingProjects =
        projectRepo.getAllNotBuildStatus(BuildStatus.SUCCESS).collectAsState(initial = emptyList())

      DashboardWidgetContent(failingProjects.value)
    }
  }
}

@Composable
fun DashboardWidgetContent(failingProjects: List<Project>) {
  val context = LocalContext.current

  GlanceTheme {
    Column(
      modifier = GlanceModifier
        .fillMaxSize()
        .background(GlanceTheme.colors.background)
    ) {

      Row(
        modifier = GlanceModifier
          .fillMaxWidth()
          .background(GlanceTheme.colors.primary)
          .clickable(onClick = actionStartActivity(MainActivity::class.java)),
        verticalAlignment = Alignment.CenterVertically
      ) {
        val title = if (failingProjects.isEmpty()) {
          context.getString(R.string.dashboard_widget_title_green)
        } else {
          context.getString(R.string.dashboard_widget_title_red, failingProjects.size.toString())
        }
        val titleStyle = TextStyle(
          color = GlanceTheme.colors.onPrimary,
          fontSize = TextUnit(16.0f, TextUnitType.Sp),
          fontWeight = FontWeight.Medium,
        )
        Image(
          provider = ImageProvider(R.drawable.ic_refresh_24),
          contentDescription = context.getString(R.string.menu_item_sync_project_status),
          colorFilter = ColorFilter.tint(GlanceTheme.colors.onPrimary),
          modifier = GlanceModifier.defaultWeight().size(48.dp).padding(12.dp)
            .clickable(onClick = actionRunCallback<WidgetRefreshAction>())
        )

        Text(
          text = title,
          style = titleStyle,
          modifier = GlanceModifier.fillMaxWidth()
        )
      }

      LazyColumn(modifier = GlanceModifier.padding(8.dp)) {
        items(failingProjects) { project ->
          Column {
            Box(
              modifier = GlanceModifier.cornerRadius(8.dp)
                .background(R.color.build_fail)
                .clickable(
                  onClick = actionStartActivity(
                    MainActivity::class.java,
                    actionParametersOf(
                      ActionParameters.Key<String>(MainActivity.INTENT_EXTRA_URL) to project.webUrl
                    )
                  )
                )
                .semantics {
                  this.testTag = "Project ${project.id} - row"
                }
            ) {
              Text(
                text = project.name,
                style = TextStyle(
                  color = GlanceTheme.colors.onError,
                  fontSize = TextUnit(12.0f, TextUnitType.Sp),
                  fontWeight = FontWeight.Normal
                ),
                maxLines = 1,
                modifier = GlanceModifier.fillMaxWidth().padding(8.dp)
              )
            }

            Box(modifier = GlanceModifier.height(8.dp)) {}
          }
        }
      }
    }
  }
}

class WidgetRefreshAction : ActionCallback {
  override suspend fun onAction(
    context: Context,
    glanceId: GlanceId,
    parameters: ActionParameters
  ) {
    SyncWorkerScheduler(context).scheduleOneTimeWork()
  }
}

@AndroidEntryPoint
class DashboardWidgetReceiver : GlanceAppWidgetReceiver() {

  @Inject
  lateinit var projectRepo: ProjectRepo

  override val glanceAppWidget: GlanceAppWidget
    get() = DashboardWidget(projectRepo)
}
