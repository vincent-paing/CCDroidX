//package dev.aungkyawpaing.ccdroidx.feature.add
//
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.text.BasicTextField
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Surface
//import androidx.compose.material3.TextField
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.testTag
//import androidx.compose.ui.test.hasTestTag
//import androidx.compose.ui.test.isRoot
//import androidx.compose.ui.test.junit4.createComposeRule
//import androidx.compose.ui.test.onNodeWithContentDescription
//import androidx.compose.ui.test.onNodeWithTag
//import androidx.compose.ui.test.onRoot
//import androidx.compose.ui.test.performClick
//import androidx.compose.ui.window.Dialog
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import com.github.takahirom.roborazzi.captureRoboImage
//import com.google.accompanist.themeadapter.material3.Mdc3Theme
//import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
//import dev.aungkyawpaing.ccdroidx._testhelper_.ScreenshotTest
//import dev.aungkyawpaing.ccdroidx.feature.add.feedurlvalidation.FeedUrlValidationResult
//import dev.aungkyawpaing.ccdroidx.feature.add.passwordvalidation.PasswordValidationResult
//import dev.aungkyawpaing.ccdroidx.feature.add.usernamevalidation.UsernameValidationResult
//import dev.aungkyawpaing.ccdroidx.testDevices
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//import org.robolectric.ParameterizedRobolectricTestRunner
//import org.robolectric.ParameterizedRobolectricTestRunner.Parameters
//import org.robolectric.RuntimeEnvironment
//import org.robolectric.annotation.GraphicsMode
//import org.robolectric.annotation.LooperMode
//
//// Cannot test on different device due to bug in Roboletric
//// See: https://github.com/robolectric/robolectric/issues/8460
//@RunWith(AndroidJUnit4::class)
//@GraphicsMode(GraphicsMode.Mode.NATIVE)
//class AddProjectDialogScreenshotTest : ScreenshotTest() {
//
//  @get:Rule
//  val composeTestRule = createComposeRule()
//
//  @Test
//  fun initialState() {
//    composeTestRule.setContent {
//      Mdc3Theme {
//        Surface(modifier = Modifier.testTag("test")){
//          AddProjectDialogContent(
//            feedUrlValidation = FeedUrlValidationResult.CORRECT,
//            usernameValidation = UsernameValidationResult.CORRECT,
//            passwordValidation = PasswordValidationResult.CORRECT,
//            isLoading = false,
//            showProjectListEvent = null,
//            onClickNext = { _, _, _, _ -> },
//            onSelectProject = {},
//            onDismissSelectProject = {},
//            EmptyDestinationsNavigator
//          )
//        }
//      }
//    }
//
//    composeTestRule.onNode(hasTestTag("test"))
//      .captureRoboImage(getScreenshotPath("initialState"))
//  }
//
////  @Test
////  fun enableRequireAuth() {
////    composeTestRule.setContent {
////      Mdc3Theme {
////        AddProjectDialogContent(
////          feedUrlValidation = FeedUrlValidationResult.CORRECT,
////          usernameValidation = UsernameValidationResult.CORRECT,
////          passwordValidation = PasswordValidationResult.CORRECT,
////          isLoading = false,
////          showProjectListEvent = null,
////          onClickNext = { _, _, _, _ -> },
////          onSelectProject = {},
////          onDismissSelectProject = {},
////          EmptyDestinationsNavigator
////        )
////      }
////    }
////
////    composeTestRule.onNodeWithTag("checkbox_requireAuth").performClick()
////
////    composeTestRule.onAllNodes(isRoot())[1]
////      .captureRoboImage(getScreenshotPath("enableRequireAuth"))
////  }
//}