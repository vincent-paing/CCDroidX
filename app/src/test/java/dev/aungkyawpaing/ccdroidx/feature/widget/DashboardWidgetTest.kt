package dev.aungkyawpaing.ccdroidx.feature.widget

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionParametersOf
import androidx.glance.appwidget.testing.unit.assertHasRunCallbackClickAction
import androidx.glance.appwidget.testing.unit.runGlanceAppWidgetUnitTest
import androidx.glance.testing.unit.assertHasStartActivityClickAction
import androidx.glance.testing.unit.hasContentDescription
import androidx.glance.testing.unit.hasTestTag
import androidx.glance.testing.unit.hasText
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.aungkyawpaing.ccdroidx._testhelper_.ProjectBuilder
import dev.aungkyawpaing.ccdroidx.feature.MainActivity
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DashboardWidgetTest {

  @Test
  fun renderWithSomeFailingProjects() = runGlanceAppWidgetUnitTest {
    setAppWidgetSize(DpSize(100.dp, 150.dp))
    setContext(ApplicationProvider.getApplicationContext())

    val failingProjects = listOf(
      ProjectBuilder.buildProject(id = 0L, name = "some project 0", webUrl = "some-url-0"),
      ProjectBuilder.buildProject(id = 1L, name = "some project 1", webUrl = "some-url-1"),
    )

    provideComposable {
      DashboardWidgetContent(failingProjects = failingProjects)
    }

    onNode(hasText("2 Red")).assertExists()
    onNode(hasText("some project 0")).assertExists()
    onNode(hasTestTag("Project 0 - row")).assertHasStartActivityClickAction<MainActivity>(
      actionParametersOf(
        ActionParameters.Key<String>(MainActivity.INTENT_EXTRA_URL) to "some-url-0"
      )
    )
    onNode(hasText("some project 1")).assertExists()
    onNode(hasTestTag("Project 1 - row")).assertHasStartActivityClickAction<MainActivity>(
      actionParametersOf(
        ActionParameters.Key<String>(MainActivity.INTENT_EXTRA_URL) to "some-url-1"
      )
    )
    onNode(hasContentDescription("Sync Project Status")).assertHasRunCallbackClickAction<WidgetRefreshAction>()
  }

  @Test
  fun renderWithNoFailingProjects() = runGlanceAppWidgetUnitTest {
    setAppWidgetSize(DpSize(100.dp, 150.dp))
    setContext(ApplicationProvider.getApplicationContext())

    provideComposable {
      DashboardWidgetContent(failingProjects = emptyList())
    }

    onNode(hasText("All Green")).assertExists()
    onNode(hasContentDescription("Sync Project Status")).assertHasRunCallbackClickAction<WidgetRefreshAction>()
  }
}