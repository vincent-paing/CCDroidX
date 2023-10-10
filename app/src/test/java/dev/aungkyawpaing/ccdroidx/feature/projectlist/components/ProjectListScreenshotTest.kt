package dev.aungkyawpaing.ccdroidx.feature.projectlist.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.captureRoboImage
import com.google.accompanist.themeadapter.material3.Mdc3Theme
import dev.aungkyawpaing.ccdroidx._testhelper_.ProjectBuilder
import dev.aungkyawpaing.ccdroidx._testhelper_.ScreenshotTest
import dev.aungkyawpaing.ccdroidx.common.BuildState
import dev.aungkyawpaing.ccdroidx.common.BuildStatus
import dev.aungkyawpaing.ccdroidx.feature.projectlist.component.ProjectList
import dev.aungkyawpaing.ccdroidx.testDevices
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner
import org.robolectric.ParameterizedRobolectricTestRunner.Parameters
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.GraphicsMode
import java.time.Clock
import java.time.ZoneId
import java.time.ZonedDateTime

@RunWith(ParameterizedRobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class ProjectListScreenshotTest(
  private val deviceName: String,
  private val deviceQualifier: String,
) : ScreenshotTest() {

  companion object {
    @JvmStatic
    @Parameters(name = "Device: {0}")
    fun testParamsProvider() = testDevices
  }

  @get:Rule
  val composeTestRule = createComposeRule()

  private val currentDateTime = ZonedDateTime.of(2023, 10, 15, 6, 6, 59, 59, ZoneId.systemDefault())
  private val fixedClock = Clock.fixed(
    currentDateTime.toInstant(), currentDateTime.zone
  )

  @Test
  fun emptyState() {
    RuntimeEnvironment.setQualifiers(deviceQualifier)
    composeTestRule.setContent {
      Mdc3Theme {
        ProjectList(
          projectList = emptyList(),
          onOpenRepoClick = { },
          onDeleteClick = { },
          onToggleMute = { },
          clock = fixedClock,
        )
      }
    }
    composeTestRule.onRoot().captureRoboImage(getScreenshotPath("emptyState_${deviceName}"))
  }

  @Test
  fun withProjects() {
    RuntimeEnvironment.setQualifiers(deviceQualifier)
    composeTestRule.setContent {
      Mdc3Theme {
        ProjectList(
          projectList = listOf(
            ProjectBuilder.buildProject(
              id = 0,
              name = "Project One",
              lastBuildStatus = BuildStatus.SUCCESS,
              lastBuildTime = currentDateTime.minusSeconds(5),
            ),
            ProjectBuilder.buildProject(
              id = 1,
              name = "Project Two",
              activity = BuildState.BUILDING,
              lastBuildStatus = BuildStatus.FAILURE,
              lastBuildTime = currentDateTime.minusMinutes(10),
            ),
            ProjectBuilder.buildProject(
              id = 2,
              name = "Project Three",
              lastBuildStatus = BuildStatus.UNKNOWN,
              lastBuildTime = currentDateTime.minusDays(5),
              lastBuildLabel = "76",
            )
          ),
          onOpenRepoClick = { },
          onDeleteClick = { },
          onToggleMute = { },
          clock = fixedClock,
        )
      }
    }

    composeTestRule.onRoot().captureRoboImage(getScreenshotPath("withProjects_${deviceName}"))
  }
}