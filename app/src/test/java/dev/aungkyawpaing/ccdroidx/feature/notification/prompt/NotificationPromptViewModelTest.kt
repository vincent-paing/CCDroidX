package dev.aungkyawpaing.ccdroidx.feature.notification.prompt

import dev.aungkyawpaing.ccdroidx._testhelper_.CoroutineTest
import dev.aungkyawpaing.ccdroidx._testhelper_.InstantTaskExecutorExtension
import dev.aungkyawpaing.ccdroidx._testhelper_.ProjectBuilder
import dev.aungkyawpaing.ccdroidx.data.ProjectRepo
import dev.aungkyawpaing.ccdroidx.feature.notification.prompt.permssionflow.NotificationPermissionFlow
import dev.aungkyawpaing.ccdroidx.observeForTesting
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.Clock
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(InstantTaskExecutorExtension::class)
class NotificationPromptViewModelTest : CoroutineTest() {

  private val projectRepo = mockk<ProjectRepo>()
  private val notificationDismissStore = mockk<NotificationDismissStore>(relaxed = true)
  private val notificationsPermissionFlow = mockk<NotificationPermissionFlow>()

  private val currentTimeClock: Clock = Clock.fixed(Instant.now(), ZoneId.systemDefault())

  private fun createViewModel(clock: Clock = currentTimeClock): NotificationPromptViewModel {
    return NotificationPromptViewModel(
      projectRepo,
      notificationsPermissionFlow,
      notificationDismissStore,
      clock,
    )
  }

  @BeforeEach
  fun setUp() {
    coEvery {
      projectRepo.getAll()
    } returns flowOf(emptyList())
    coEvery {
      notificationDismissStore.getDismissTimeStamp()
    } returns flowOf(LocalDateTime.now(currentTimeClock).minusDays(14).minusNanos(1L))
    every {
      notificationsPermissionFlow.getFlow()
    }.returns(flowOf(false))
  }


  @Nested
  @DisplayName("promptIsVisibleLiveData")
  internal inner class PromptIsVisibleLiveData {

    @Test
    fun `hide prompt if there are no projects`() = runTest {
      coEvery {
        projectRepo.getAll()
      } returns flowOf(emptyList())

      val viewModel = createViewModel()

      viewModel.promptIsVisibleLiveData.observeForTesting {
        runCurrent()
        Assertions.assertEquals(false, viewModel.promptIsVisibleLiveData.value)
      }
    }

    @Nested
    @DisplayName("When there are at least one project")
    internal inner class AtLeastOneProject {

      @BeforeEach
      fun setup() {
        coEvery {
          projectRepo.getAll()
        } returns flowOf(listOf(ProjectBuilder.buildProject()))
      }

      @Test
      fun `show prompt if last dismiss timestamp has not been saved yet`() = runTest {
        coEvery {
          notificationDismissStore.getDismissTimeStamp()
        } returns flowOf(null)

        val viewModel = createViewModel(currentTimeClock)

        viewModel.promptIsVisibleLiveData.observeForTesting {
          runCurrent()
          Assertions.assertEquals(true, viewModel.promptIsVisibleLiveData.value)
        }
      }

      @Test
      fun `hide prompt if last dismiss time is within past 14 days`() = runTest {
        coEvery {
          notificationDismissStore.getDismissTimeStamp()
        } returns flowOf(LocalDateTime.now(currentTimeClock).minusDays(13))

        val viewModel = createViewModel(currentTimeClock)

        viewModel.promptIsVisibleLiveData.observeForTesting {
          runCurrent()
          Assertions.assertEquals(false, viewModel.promptIsVisibleLiveData.value)
        }
      }

      @Nested
      @DisplayName("When last dismiss time is not within past 14 days")
      internal inner class Within14Days {

        @BeforeEach
        fun setUp() {
          coEvery {
            notificationDismissStore.getDismissTimeStamp()
          } returns flowOf(LocalDateTime.now(currentTimeClock).minusDays(14).minusNanos(1L))
        }

        @Test
        fun `hide prompt if notification is enabled already`() = runTest {
          every {
            notificationsPermissionFlow.getFlow()
          } returns flowOf(true)
          val viewModel = createViewModel(currentTimeClock)

          viewModel.promptIsVisibleLiveData.observeForTesting {
            runCurrent()
            Assertions.assertEquals(false, viewModel.promptIsVisibleLiveData.value)
          }
        }

        @Test
        fun `show prompt if notification is disabled`() = runTest {
          every {
            notificationsPermissionFlow.getFlow()
          } returns flowOf(false)
          val viewModel = createViewModel(currentTimeClock)

          viewModel.promptIsVisibleLiveData.observeForTesting {
            runCurrent()
            Assertions.assertEquals(true, viewModel.promptIsVisibleLiveData.value)
          }
        }

      }
    }
  }

  @Test
  fun `save current time on clicking dismiss`() = runTest {
    val viewModel = createViewModel()

    viewModel.onDismissClick()

    runCurrent()

    coVerify(exactly = 1) {
      notificationDismissStore.saveDismissTimeStamp(LocalDateTime.now(currentTimeClock))
    }
  }

}