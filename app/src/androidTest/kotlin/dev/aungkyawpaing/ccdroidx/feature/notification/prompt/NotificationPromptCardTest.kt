package dev.aungkyawpaing.ccdroidx.feature.notification.prompt

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import dev.aungkyawpaing.ccdroidx.R
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.DisplayName

class NotificationPromptCardTest {

  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  @DisplayName("render Notification Prompt Card")
  fun renderNotificationPromptCard() {

    composeTestRule.setContent {
      NotificationPromptCard({}, {})
    }

    composeTestRule.onNodeWithText("Enable notification to receive alerts when your pipelines status changes")
      .assertIsDisplayed()
    composeTestRule.onNodeWithText("ENABLE NOTIFICATION").assertIsDisplayed()
  }

  @Test
  @DisplayName("invoke onDismissPrompt on clicking cross")
  fun invokeOnDismissPromptOnClickingCross() {

    val onDismissPrompt = mockk<() -> Unit>(relaxed = true)

    composeTestRule.setContent {
      NotificationPromptCard(onDismissPrompt = onDismissPrompt, onEnableNotification = {})
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
  @DisplayName("invoke onEnableNotification on clicking enable notification")
  fun invokeOnEnableNotificationOnClickingEnableNotification() {

    val onEnableNotification = mockk<() -> Unit>(relaxed = true)

    composeTestRule.setContent {
      NotificationPromptCard(onDismissPrompt = {}, onEnableNotification = onEnableNotification)
    }

    composeTestRule.onNodeWithText("ENABLE NOTIFICATION").performClick()

    verify(exactly = 1) {
      onEnableNotification()
    }
  }
}