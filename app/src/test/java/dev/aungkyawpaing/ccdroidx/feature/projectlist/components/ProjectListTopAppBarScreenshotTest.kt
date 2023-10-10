package dev.aungkyawpaing.ccdroidx.feature.projectlist.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.captureRoboImage
import com.google.accompanist.themeadapter.material3.Mdc3Theme
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import dev.aungkyawpaing.ccdroidx._testhelper_.ScreenshotTest
import dev.aungkyawpaing.ccdroidx.feature.projectlist.component.ProjectListTopAppBar
import dev.aungkyawpaing.ccdroidx.feature.sync.LastSyncedState
import dev.aungkyawpaing.ccdroidx.feature.sync.LastSyncedStatus
import dev.aungkyawpaing.ccdroidx.testDevices
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.GraphicsMode
import java.time.Clock
import java.time.ZoneId
import java.time.ZonedDateTime

@RunWith(ParameterizedRobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class ProjectListTopAppBarScreenshotTest(
  private val deviceName: String,
  private val deviceQualifier: String,
) : ScreenshotTest() {

  companion object {
    @JvmStatic
    @ParameterizedRobolectricTestRunner.Parameters(name = "Device: {0}")
    fun testParamsProvider() = testDevices
  }

  @get:Rule
  val composeTestRule = createComposeRule()

  private val currentDateTime = ZonedDateTime.of(2023, 10, 15, 6, 6, 59, 59, ZoneId.systemDefault())
  private val fixedClock = Clock.fixed(
    currentDateTime.toInstant(), currentDateTime.zone
  )

  @Test
  fun initialState() {
    RuntimeEnvironment.setQualifiers(deviceQualifier)
    composeTestRule.setContent {
      Mdc3Theme {
        ProjectListTopAppBar(
          lastSyncedStatus = null,
          onProgressSyncedEvent = false,
          onPressSync = {},
          clearOnProgressSyncedEvent = {},
          navigator = EmptyDestinationsNavigator,
          clock = fixedClock,
        )
      }
    }
    composeTestRule.onRoot().captureRoboImage(getScreenshotPath("initialState_${deviceName}"))
  }

  @Test
  fun syncing() {
    RuntimeEnvironment.setQualifiers(deviceQualifier)
    composeTestRule.setContent {
      Mdc3Theme {
        ProjectListTopAppBar(
          lastSyncedStatus = LastSyncedStatus(
            lastSyncedDateTime = currentDateTime.minusMinutes(5L),
            lastSyncedState = LastSyncedState.SYNCING
          ),
          onProgressSyncedEvent = false,
          onPressSync = {},
          clearOnProgressSyncedEvent = {},
          navigator = EmptyDestinationsNavigator,
          clock = fixedClock,
        )
      }
    }

    composeTestRule.onRoot().captureRoboImage(getScreenshotPath("syncing_${deviceName}"))
  }

  @Test
  fun syncedSuccess() {
    RuntimeEnvironment.setQualifiers(deviceQualifier)
    composeTestRule.setContent {
      Mdc3Theme {
        ProjectListTopAppBar(
          lastSyncedStatus = LastSyncedStatus(
            lastSyncedDateTime = currentDateTime.minusMinutes(5L),
            lastSyncedState = LastSyncedState.SUCCESS
          ),
          onProgressSyncedEvent = false,
          onPressSync = {},
          clearOnProgressSyncedEvent = {},
          navigator = EmptyDestinationsNavigator,
          clock = fixedClock,
        )
      }
    }

    composeTestRule.onRoot().captureRoboImage(getScreenshotPath("syncedSuccess_${deviceName}"))
  }

  @Test
  fun syncedFailed() {
    RuntimeEnvironment.setQualifiers(deviceQualifier)
    composeTestRule.setContent {
      Mdc3Theme {
        ProjectListTopAppBar(
          lastSyncedStatus = LastSyncedStatus(
            lastSyncedDateTime = currentDateTime.minusMinutes(5L),
            lastSyncedState = LastSyncedState.FAILED
          ),
          onProgressSyncedEvent = false,
          onPressSync = {},
          clearOnProgressSyncedEvent = {},
          navigator = EmptyDestinationsNavigator,
          clock = fixedClock,
        )
      }
    }

    composeTestRule.onRoot().captureRoboImage(getScreenshotPath("syncedFailed_${deviceName}"))
  }
}