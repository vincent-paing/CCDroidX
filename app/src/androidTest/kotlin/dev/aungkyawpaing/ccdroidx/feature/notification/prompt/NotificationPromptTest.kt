package dev.aungkyawpaing.ccdroidx.feature.notification.prompt

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.intent.matcher.IntentMatchers.hasFlag
import dev.aungkyawpaing.ccdroidx.R
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.CoreMatchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.DisplayName

class NotificationPromptTest {

  @get:Rule
  val composeTestRule = createComposeRule()

  private val notificationPromptText = ApplicationProvider.getApplicationContext<Context>()
    .getString(R.string.notification_prompt_body)

  @Test
  @DisplayName("does not render Notification Prompt Card when prompt should not be visible")
  fun doesNotRenderNotificationPromptCardWhenPromptIsNotVisible() {
    composeTestRule.setContent {
      NotificationPrompt(
        false,
        {}
      )
    }

    composeTestRule.onNodeWithText(notificationPromptText).assertDoesNotExist()
  }

  @Test
  @DisplayName("render Notification Prompt Card when prompt should be visible")
  fun renderNotificationCardWhenPromptIsVisible() {
    composeTestRule.setContent {
      NotificationPrompt(
        true,
        {}
      )
    }

    composeTestRule.onNodeWithText(notificationPromptText).assertIsDisplayed()
    composeTestRule.onNodeWithText("ENABLE NOTIFICATION").assertIsDisplayed()
  }

  @Test
  @DisplayName("invoke onDismissClick on clicking dismiss")
  fun invokeOnDismissClickOnClickingDismiss() {
    val onDismissPrompt = mockk<() -> Unit>(relaxed = true)
    composeTestRule.setContent {
      NotificationPrompt(
        true,
        onDismissPrompt
      )
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
    val context = ApplicationProvider.getApplicationContext<Context>()
    Intents.init()

    composeTestRule.setContent {
      NotificationPrompt(
        true,
        {}
      )
    }

    composeTestRule.onNodeWithText("ENABLE NOTIFICATION").performClick()

    Intents.intended(
      allOf(
        hasAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS),
        hasExtra(Settings.EXTRA_APP_PACKAGE, context.packageName),
        hasFlag(Intent.FLAG_ACTIVITY_NEW_TASK)
      )
    )

    Intents.release()
  }
}