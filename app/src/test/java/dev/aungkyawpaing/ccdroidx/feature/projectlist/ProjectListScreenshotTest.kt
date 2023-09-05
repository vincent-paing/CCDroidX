package dev.aungkyawpaing.ccdroidx.feature.projectlist

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.captureRoboImage
import com.google.accompanist.themeadapter.material3.Mdc3Theme
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import dev.aungkyawpaing.ccdroidx._testhelper_.ProjectBuilder
import dev.aungkyawpaing.ccdroidx._testhelper_.screenshotPath
import dev.aungkyawpaing.ccdroidx.common.BuildStatus
import dev.aungkyawpaing.ccdroidx.feature.sync.LastSyncedState
import dev.aungkyawpaing.ccdroidx.feature.sync.LastSyncedStatus
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
) {

  companion object {
    @JvmStatic
    @Parameters(name = "Device: {0}")
    fun testParamsProvider() = testDevices
  }

  @get:Rule
  val composeTestRule = createComposeRule()

  private val currentDateTime = ZonedDateTime.of(2023, 10, 15, 6, 6, 59, 59, ZoneId.systemDefault())

  @Test
  fun emptyState() {
    RuntimeEnvironment.setQualifiers(deviceQualifier)
    composeTestRule.setContent {
      Mdc3Theme {
        ProjectListPageContent(
          projectList = emptyList(),
          lastSyncedStatus = null,
          onProgressSyncedEvent = false,
          onToggleMute = { },
          onPressSync = { },
          clearOnProgressSyncedEvent = { },
          onDeleteProject = { },
          isNotificationPromptVisible = false,
          onDismissNotificationPrompt = { },
          navigator = EmptyDestinationsNavigator
        )
      }
    }
    composeTestRule.onRoot()
      .captureRoboImage(screenshotPath("projectList_emptyState_${deviceName}"))
  }

  @Test
  fun withProjects() {
    RuntimeEnvironment.setQualifiers(deviceQualifier)
    composeTestRule.setContent {
      Mdc3Theme {
        ProjectListPageContent(
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
              lastBuildStatus = BuildStatus.FAILURE,
              lastBuildTime = currentDateTime.minusMinutes(5),
            ),
            ProjectBuilder.buildProject(
              id = 2,
              name = "Project Three",
              lastBuildStatus = BuildStatus.UNKNOWN,
              lastBuildTime = currentDateTime.minusDays(5),
            ),
            ProjectBuilder.buildProject(
              id = 3,
              name = "Project Four",
              lastBuildStatus = BuildStatus.EXCEPTION,
              lastBuildLabel = "76",
              lastBuildTime = currentDateTime.minusYears(1),
            )
          ),
          lastSyncedStatus = null,
          onProgressSyncedEvent = false,
          onToggleMute = { },
          onPressSync = { },
          clearOnProgressSyncedEvent = { },
          onDeleteProject = { },
          isNotificationPromptVisible = true,
          onDismissNotificationPrompt = { },
          navigator = EmptyDestinationsNavigator,
          clock = Clock.fixed(
            currentDateTime.toInstant(),
            currentDateTime.zone
          )
        )
      }
    }
    composeTestRule.onRoot()
      .captureRoboImage(screenshotPath("projectList_withProjects_${deviceName}"))
  }

  @Test
  fun syncing() {
    RuntimeEnvironment.setQualifiers(deviceQualifier)
    composeTestRule.setContent {
      Mdc3Theme {
        ProjectListPageContent(
          projectList = emptyList(),
          lastSyncedStatus = LastSyncedStatus(
            currentDateTime.minusMinutes(5),
            LastSyncedState.SYNCING,
          ),
          onProgressSyncedEvent = false,
          onToggleMute = { },
          onPressSync = { },
          clearOnProgressSyncedEvent = { },
          onDeleteProject = { },
          isNotificationPromptVisible = false,
          onDismissNotificationPrompt = { },
          navigator = EmptyDestinationsNavigator
        )
      }
    }
    composeTestRule.onRoot()
      .captureRoboImage(screenshotPath("projectList_syncing_${deviceName}"))
  }

  @Test
  fun synced() {
    RuntimeEnvironment.setQualifiers(deviceQualifier)
    composeTestRule.setContent {
      Mdc3Theme {
        ProjectListPageContent(
          projectList = emptyList(),
          lastSyncedStatus = LastSyncedStatus(
            currentDateTime.minusMinutes(5),
            LastSyncedState.SUCCESS,
          ),
          onProgressSyncedEvent = false,
          onToggleMute = { },
          onPressSync = { },
          clearOnProgressSyncedEvent = { },
          onDeleteProject = { },
          isNotificationPromptVisible = false,
          onDismissNotificationPrompt = { },
          navigator = EmptyDestinationsNavigator,
          clock = Clock.fixed(
            currentDateTime.toInstant(),
            currentDateTime.zone
          )
        )
      }
    }
    composeTestRule.onRoot()
      .captureRoboImage(screenshotPath("projectList_synced_${deviceName}"))
  }
}