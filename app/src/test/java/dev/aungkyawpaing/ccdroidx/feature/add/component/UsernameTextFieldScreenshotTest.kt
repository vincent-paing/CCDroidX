package dev.aungkyawpaing.ccdroidx.feature.add.component

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.captureRoboImage
import com.google.accompanist.themeadapter.material3.Mdc3Theme
import dev.aungkyawpaing.ccdroidx._testhelper_.ScreenshotTest
import dev.aungkyawpaing.ccdroidx.feature.add.usernamevalidation.UsernameValidationResult
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
class UsernameTextFieldScreenshotTest(
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
  fun initialState() {
    RuntimeEnvironment.setQualifiers(deviceQualifier)
    composeTestRule.setContent {
      Mdc3Theme {
        UsernameTextField(
          value = "",
          isEnabled = true,
          usernameValidation = UsernameValidationResult.CORRECT,
          onValueChange = {}
        )
      }
    }
    composeTestRule.onRoot()
      .captureRoboImage(getScreenshotPath("initialState_${deviceName}"))
  }

  @Test
  fun disabledState() {
    RuntimeEnvironment.setQualifiers(deviceQualifier)
    composeTestRule.setContent {
      Mdc3Theme {
        UsernameTextField(
          value = "I am disabled",
          isEnabled = false,
          usernameValidation = UsernameValidationResult.CORRECT,
          onValueChange = {}
        )
      }
    }
    composeTestRule.onRoot()
      .captureRoboImage(getScreenshotPath("disabledState_${deviceName}"))
  }

  @Test
  fun validInput() {
    RuntimeEnvironment.setQualifiers(deviceQualifier)
    composeTestRule.setContent {
      Mdc3Theme {
        UsernameTextField(
          value = "https://valid_url.com",
          isEnabled = true,
          usernameValidation = UsernameValidationResult.CORRECT,
          onValueChange = {}
        )
      }
    }
    composeTestRule.onRoot()
      .captureRoboImage(getScreenshotPath("validInput_${deviceName}"))
  }

  @Test
  fun validInputLongText() {
    RuntimeEnvironment.setQualifiers(deviceQualifier)
    composeTestRule.setContent {
      Mdc3Theme {
        UsernameTextField(
          value = "https://valid_url.com/i_am_very_long/super_duper_long_url",
          isEnabled = true,
          usernameValidation = UsernameValidationResult.CORRECT,
          onValueChange = {}
        )
      }
    }
    composeTestRule.onRoot()
      .captureRoboImage(getScreenshotPath("validInputLongText_${deviceName}"))
  }

  @Test
  fun invalidInputEmptyText() {
    RuntimeEnvironment.setQualifiers(deviceQualifier)
    composeTestRule.setContent {
      Mdc3Theme {
        UsernameTextField(
          value = "",
          isEnabled = true,
          usernameValidation = UsernameValidationResult.INCORRECT_EMPTY_TEXT,
          onValueChange = {}
        )
      }
    }
    composeTestRule.onRoot()
      .captureRoboImage(getScreenshotPath("invalidInputEmptyText_${deviceName}"))
  }

}