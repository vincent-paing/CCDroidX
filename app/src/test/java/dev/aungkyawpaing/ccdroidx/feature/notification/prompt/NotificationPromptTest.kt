package dev.aungkyawpaing.ccdroidx.feature.notification.prompt

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.lifecycle.liveData
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.composethemeadapter3.Mdc3Theme
import dev.aungkyawpaing.ccdroidx.R
import dev.aungkyawpaing.ccdroidx.roboeletric.FakeAndroidKeyStore
import io.mockk.every
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
class NotificationPromptTest {

  @get:Rule
  val composeTestRule = createComposeRule()

  companion object {
    @JvmStatic
    @BeforeClass
    fun beforeClass() {
      FakeAndroidKeyStore.setup
    }
  }

  private val notificationPromptText = ApplicationProvider.getApplicationContext<Context>()
    .getString(R.string.notification_prompt_body)

  private val notificationPromptViewModel = mockk<NotificationPromptViewModel>(relaxUnitFun = true)

  @Test
  fun `does not render Notification Prompt Card when prompt should not be visible`() {

    every {
      notificationPromptViewModel.promptIsVisibleLiveData
    } returns liveData {
      emit(false)
    }

    composeTestRule.setContent {
      Mdc3Theme {
        NotificationPrompt(viewModel = notificationPromptViewModel)
      }
    }

    composeTestRule.onNodeWithText(notificationPromptText).assertDoesNotExist()
  }

  @Test
  fun `render Notification Prompt Card when prompt should be visible`() {
    every {
      notificationPromptViewModel.promptIsVisibleLiveData
    } returns liveData {
      emit(true)
    }

    composeTestRule.setContent {
      Mdc3Theme {
        NotificationPrompt(viewModel = notificationPromptViewModel)
      }
    }

    composeTestRule.onNodeWithText(notificationPromptText).assertIsDisplayed()
  }

  @Test
  fun `invoke onDismissClick from viewModel on clicking dismiss`() {
    every {
      notificationPromptViewModel.promptIsVisibleLiveData
    } returns liveData {
      emit(true)
    }

    composeTestRule.setContent {
      Mdc3Theme {
        NotificationPrompt(viewModel = notificationPromptViewModel)
      }
    }

    val contentDescription = ApplicationProvider.getApplicationContext<Context>()
      .getString(R.string.notification_prompt_close_content_description)
    composeTestRule.onNodeWithContentDescription(contentDescription).performClick()

    verify(exactly = 1) {
      notificationPromptViewModel.onDismissClick()
    }
  }
}