package dev.aungkyawpaing.ccdroidx.feature.notification.prompt

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.lifecycle.liveData
import androidx.test.core.app.ApplicationProvider
import dev.aungkyawpaing.ccdroidx.R
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.DisplayName

class NotificationPromptTest {

  @get:Rule
  val composeTestRule = createComposeRule()

  private val notificationPromptText = ApplicationProvider.getApplicationContext<Context>()
    .getString(R.string.notification_prompt_body)

  private val notificationPromptViewModel = mockk<NotificationPromptViewModel>(relaxUnitFun = true)

  @Test
  @DisplayName("does not render Notification Prompt Card when prompt should not be visible")
  fun doesNotRenderNotificationPromptCardWhenPromptIsNotVisible() {

    every {
      notificationPromptViewModel.promptIsVisibleLiveData
    } returns liveData {
      emit(false)
    }

    composeTestRule.setContent {
      NotificationPrompt(viewModel = notificationPromptViewModel)
    }

    composeTestRule.onNodeWithText(notificationPromptText).assertDoesNotExist()
  }

  @Test
  @DisplayName("render Notification Prompt Card when prompt should be visible")
  fun renderNotificationCardWhenPromptIsVisible() {
    every {
      notificationPromptViewModel.promptIsVisibleLiveData
    } returns liveData {
      emit(true)
    }

    composeTestRule.setContent {
      NotificationPrompt(viewModel = notificationPromptViewModel)
    }

    composeTestRule.onNodeWithText(notificationPromptText).assertIsDisplayed()
  }

  @Test
  @DisplayName("invoke onDismissClick on clicking dismiss")
  fun invokeOnDismissClickOnClickingDismiss() {
    every {
      notificationPromptViewModel.promptIsVisibleLiveData
    } returns liveData {
      emit(true)
    }

    composeTestRule.setContent {
      NotificationPrompt(viewModel = notificationPromptViewModel)
    }

    val contentDescription = ApplicationProvider.getApplicationContext<Context>()
      .getString(R.string.notification_prompt_close_content_description)
    composeTestRule.onNodeWithContentDescription(contentDescription).performClick()

    verify(exactly = 1) {
      notificationPromptViewModel.onDismissClick()
    }
  }
}