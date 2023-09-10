package dev.aungkyawpaing.ccdroidx.feature.add

import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.takahirom.roborazzi.captureRoboImage
import com.google.accompanist.themeadapter.material3.Mdc3Theme
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import dev.aungkyawpaing.ccdroidx._testhelper_.ScreenshotTest
import dev.aungkyawpaing.ccdroidx.feature.add.feedurlvalidation.FeedUrlValidationResult
import dev.aungkyawpaing.ccdroidx.feature.add.passwordvalidation.PasswordValidationResult
import dev.aungkyawpaing.ccdroidx.feature.add.usernamevalidation.UsernameValidationResult
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.GraphicsMode

// Cannot test on different device due to bug in Roboletric
// See: https://github.com/robolectric/robolectric/issues/8460
@RunWith(AndroidJUnit4::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class AddProjectDialogScreenshotTest : ScreenshotTest() {

  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun initialState() {
    composeTestRule.setContent {
      Mdc3Theme {
        Surface(modifier = Modifier.testTag("test")) {
          AddProjectDialogContent(
            feedUrlValidation = FeedUrlValidationResult.CORRECT,
            usernameValidation = UsernameValidationResult.CORRECT,
            passwordValidation = PasswordValidationResult.CORRECT,
            isLoading = false,
            showProjectListEvent = null,
            onClickNext = { _, _, _, _ -> },
            onSelectProject = {},
            onDismissSelectProject = {},
            EmptyDestinationsNavigator
          )
        }
      }
    }

    composeTestRule.onNode(hasTestTag("test"))
      .captureRoboImage(getScreenshotPath("initialState"))
  }

  @Test
  fun enableRequireAuth() {
    composeTestRule.setContent {
      Mdc3Theme {
        AddProjectDialogContent(
          feedUrlValidation = FeedUrlValidationResult.CORRECT,
          usernameValidation = UsernameValidationResult.CORRECT,
          passwordValidation = PasswordValidationResult.CORRECT,
          isLoading = false,
          showProjectListEvent = null,
          onClickNext = { _, _, _, _ -> },
          onSelectProject = {},
          onDismissSelectProject = {},
          EmptyDestinationsNavigator
        )
      }
    }

    composeTestRule.onNodeWithTag("checkbox-require-auth", useUnmergedTree = true).performClick()
    composeTestRule.onRoot()
      .captureRoboImage(getScreenshotPath("enableRequireAuth"))
  }

  @Test
  fun loadingState() {
    composeTestRule.setContent {
      Mdc3Theme {
        AddProjectDialogContent(
          feedUrlValidation = FeedUrlValidationResult.CORRECT,
          usernameValidation = UsernameValidationResult.CORRECT,
          passwordValidation = PasswordValidationResult.CORRECT,
          isLoading = true,
          showProjectListEvent = null,
          onClickNext = { _, _, _, _ -> },
          onSelectProject = {},
          onDismissSelectProject = {},
          EmptyDestinationsNavigator
        )
      }
    }

    composeTestRule.onNodeWithTag("checkbox-require-auth", useUnmergedTree = true).performClick()
    composeTestRule.onRoot()
      .captureRoboImage(getScreenshotPath("loadingState"))
  }
}