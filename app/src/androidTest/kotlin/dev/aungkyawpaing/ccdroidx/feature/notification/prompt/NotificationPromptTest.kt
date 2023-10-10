package dev.aungkyawpaing.ccdroidx.feature.notification.prompt

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.intent.matcher.IntentMatchers.hasFlag
import de.mannodermaus.junit5.compose.createComposeExtension
import dev.aungkyawpaing.ccdroidx.R
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.allOf
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

@ExperimentalTestApi
class NotificationPromptTest {

  @JvmField
  @RegisterExtension
  val extension = createComposeExtension()

  val notificationPromptViewModel: NotificationPromptViewModel = mockk(relaxed = true)

  private val notificationPromptText = ApplicationProvider.getApplicationContext<Context>()
    .getString(R.string.notification_prompt_body)

  @Test
  @DisplayName("does not render Notification Prompt Card when prompt should not be visible")
  fun doesNotRenderNotificationPromptCardWhenPromptIsNotVisible() = runTest {
    every {
      notificationPromptViewModel.promptIsVisible
    } returns flowOf(false).stateIn(this)

    extension.use {
      setContent {
        NotificationPrompt(
          notificationPromptViewModel
        )
      }

      onNodeWithText(notificationPromptText).assertDoesNotExist()
    }
  }

  @Nested
  @DisplayName("When prompt should be visible")
  internal inner class WhenPromptIsVisible {

    @BeforeEach
    fun setUp() = runTest {
      every {
        notificationPromptViewModel.promptIsVisible
      } returns flowOf(true).stateIn(this)
    }

    @Test
    @DisplayName("render Notification Prompt Card ")
    fun renderNotificationCardWhenPromptIsVisible() {
      extension.use {
        setContent {
          NotificationPrompt(
            notificationPromptViewModel
          )
        }

        onNodeWithText(notificationPromptText).assertIsDisplayed()
      }
    }

    @Test
    @DisplayName("invoke onDismissClick on clicking dismiss")
    fun invokeOnDismissClickOnClickingDismiss() {
      extension.use {
        setContent {
          NotificationPrompt(
            notificationPromptViewModel
          )
        }

        val contentDescription = ApplicationProvider.getApplicationContext<Context>()
          .getString(R.string.notification_prompt_close_content_description)
        onNodeWithContentDescription(contentDescription).assertIsDisplayed()
        onNodeWithContentDescription(contentDescription).performClick()
      }

      verify(exactly = 1) {
        notificationPromptViewModel.onDismissClick()
      }
    }

    @Test
    @DisplayName("open notification settings on clicking enable notification")
    fun invokeOnEnableNotificationOnClickingEnableNotification() {
      val context = ApplicationProvider.getApplicationContext<Context>()
      Intents.init()

      extension.use {
        setContent {
          NotificationPrompt(
            notificationPromptViewModel
          )
        }

        onNodeWithText("ENABLE NOTIFICATION").performClick()
      }

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
}