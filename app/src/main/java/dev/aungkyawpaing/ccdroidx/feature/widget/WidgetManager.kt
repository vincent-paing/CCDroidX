package dev.aungkyawpaing.ccdroidx.feature.widget

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetManager
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.aungkyawpaing.ccdroidx.data.ProjectRepo
import javax.inject.Inject

class WidgetManager @Inject constructor(
  @ApplicationContext private val context: Context,
  private val projectRepo: ProjectRepo,
) {
  suspend fun updateDashboardWidget() {
    val manager = GlanceAppWidgetManager(context)
    val glanceIds = manager.getGlanceIds(DashboardWidget::class.java)
    glanceIds.forEach { glanceId ->
      DashboardWidget(projectRepo).update(context, glanceId)
    }
  }
}
