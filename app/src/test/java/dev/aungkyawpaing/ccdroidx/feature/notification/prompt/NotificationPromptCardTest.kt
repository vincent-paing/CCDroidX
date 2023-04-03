package dev.aungkyawpaing.ccdroidx.feature.notification.prompt

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.composethemeadapter3.Mdc3Theme
import dev.aungkyawpaing.ccdroidx.R
import dev.aungkyawpaing.ccdroidx.roboeletric.FakeAndroidKeyStore
import io.mockk.mockk
import io.mockk.verify
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(
  instrumentedPackages = ["androidx.loader.content"]
)
class NotificationPromptCardTest {

  @get:Rule
  val composeTestRule = createComposeRule()

  companion object {
    @JvmStatic
    @BeforeClass
    fun beforeClass() {
      FakeAndroidKeyStore.setup
    }
  }

  @Test
  fun `render Notification Prompt Card`() {

    composeTestRule.setContent {
      Mdc3Theme {
        NotificationPromptCard({}, {})
      }
    }

    composeTestRule.onNodeWithText("Enable notification to receive alerts when your pipelines status changes")
      .assertIsDisplayed()
    composeTestRule.onNodeWithText("ENABLE NOTIFICATION").assertIsDisplayed()
  }

  @Test
  fun `invoke onDismissPrompt on clicking cross`() {

    val onDismissPrompt = mockk<() -> Unit>(relaxed = true)

    composeTestRule.setContent {
      Mdc3Theme {
        NotificationPromptCard(onDismissPrompt = onDismissPrompt, onEnableNotification =  {})
      }
    }

    val contentDescription = ApplicationProvider.getApplicationContext<Context>()
      .getString(R.string.notification_prompt_close_content_description)
    composeTestRule.onNodeWithContentDescription(contentDescription).assertIsDisplayed()
    composeTestRule.onNodeWithContentDescription(contentDescription).performClick()

    verify(exactly = 1) {
      onDismissPrompt()
    }
  }

  @Test
  fun `invoke onEnableNotification on clicking enable notification`() {

    val onEnableNotification = mockk<() -> Unit>(relaxed = true)

    composeTestRule.setContent {
      Mdc3Theme {
        NotificationPromptCard(onDismissPrompt = {}, onEnableNotification = onEnableNotification)
      }
    }

    composeTestRule.onNodeWithText("ENABLE NOTIFICATION").performClick()

    verify(exactly = 1) {
      onEnableNotification()
    }
  }
}