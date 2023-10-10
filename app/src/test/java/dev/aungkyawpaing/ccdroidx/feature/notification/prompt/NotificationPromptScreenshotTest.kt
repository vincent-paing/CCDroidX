package dev.aungkyawpaing.ccdroidx.feature.notification.prompt

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.captureRoboImage
import com.google.accompanist.themeadapter.material3.Mdc3Theme
import dev.aungkyawpaing.ccdroidx._testhelper_.ScreenshotTest
import dev.aungkyawpaing.ccdroidx.testDevices
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner
import org.robolectric.ParameterizedRobolectricTestRunner.Parameters
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.GraphicsMode

@RunWith(ParameterizedRobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class NotificationPromptScreenshotTest(
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

  @Test
  fun notificationPromptCard() {
    RuntimeEnvironment.setQualifiers(deviceQualifier)
    composeTestRule.setContent {
      Mdc3Theme {
        NotificationPromptContent(
          onDismissPrompt = {},
          onEnableNotification = {}
        )
      }
    }

    composeTestRule.onRoot()
      .captureRoboImage(getScreenshotPath("notificationPrompt_${deviceName}"))
  }
}