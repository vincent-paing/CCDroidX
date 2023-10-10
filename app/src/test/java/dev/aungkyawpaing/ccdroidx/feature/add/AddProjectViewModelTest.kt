package dev.aungkyawpaing.ccdroidx.feature.add

import dev.aungkyawpaing.ccdroidx._testhelper_.CoroutineTest
import dev.aungkyawpaing.ccdroidx._testhelper_.InstantTaskExecutorExtension
import dev.aungkyawpaing.ccdroidx._testhelper_.ProjectBuilder
import dev.aungkyawpaing.ccdroidx.data.ProjectRepo
import dev.aungkyawpaing.ccdroidx.feature.add.feedurlvalidation.FeedUrlValidationResult
import dev.aungkyawpaing.ccdroidx.feature.add.passwordvalidation.PasswordValidationResult
import dev.aungkyawpaing.ccdroidx.feature.add.usernamevalidation.UsernameValidationResult
import dev.aungkyawpaing.ccdroidx.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(InstantTaskExecutorExtension::class)
class AddProjectViewModelTest : CoroutineTest() {

  private val projectRepo = mockk<ProjectRepo>(relaxed = true)
  private val mockValidator = mockk<AddProjectInputValidator>()

  private val viewModel =
    AddProjectViewModel(
      projectRepo,
      mockk(),
      mockk(),
      mockValidator,
      testDispatcherProvider,
    )

  @Nested
  @DisplayName("When fetchRepo is successful")
  internal inner class FetchRepoSuccessful {

    private val projectList = listOf(
      ProjectBuilder.buildProject(-1L)
    )

    @BeforeEach
    internal fun setUp() {
      coEvery {
        projectRepo.fetchRepo(any(), any(), any())
      } returns projectList
    }

    @Nested
    @DisplayName("When Require Auth is disabled")
    internal inner class RequireAuthDisabled {

      @Test
      fun `invoke fetchRepo with feed url only`() = runTest {
        coEvery {
          mockValidator.validateFeedUrl(any())
        } returns FeedUrlValidationResult.CORRECT

        viewModel.onClickNext("url", false, "", "");

        runCurrent()

        coVerify(exactly = 1) {
          projectRepo.fetchRepo("url")
        }
      }

      @Test
      fun `reset username and password validation to correct`() = runTest {
        coEvery {
          mockValidator.validateFeedUrl(any())
        } returns FeedUrlValidationResult.CORRECT
        coEvery {
          mockValidator.validatePassword(any())
        } returns PasswordValidationResult.INCORRECT_EMPTY_TEXT
        coEvery {
          mockValidator.validateUsername(any())
        } returns UsernameValidationResult.INCORRECT_EMPTY_TEXT

        viewModel.usernameValidationResult.value = UsernameValidationResult.INCORRECT_EMPTY_TEXT
        viewModel.passwordValidationResult.value = PasswordValidationResult.INCORRECT_EMPTY_TEXT
        viewModel.onClickNext("url", false, "", "")

        runCurrent()
        Assertions.assertEquals(
          UsernameValidationResult.CORRECT,
          viewModel.usernameValidationResult.getOrAwaitValue()
        )
        Assertions.assertEquals(
          PasswordValidationResult.CORRECT,
          viewModel.passwordValidationResult.getOrAwaitValue()
        )
      }

    }
  }

  @Nested
  @DisplayName("When Require Auth is enabled")
  internal inner class RequireAuthEnabled {

    @BeforeEach
    fun setup() {
      coEvery {
        mockValidator.validateFeedUrl(any())
      } returns FeedUrlValidationResult.CORRECT
    }

    @Test
    fun `invoke fetchRepo with feed url, username and password`() = runTest {
      coEvery {
        mockValidator.validateUsername(any())
      } returns UsernameValidationResult.CORRECT

      coEvery {
        mockValidator.validatePassword(any())
      } returns PasswordValidationResult.CORRECT

      viewModel.onClickNext("url", true, "username", "password");

      runCurrent()

      coVerify(exactly = 1) {
        projectRepo.fetchRepo("url", "username", "password")
      }
    }

    @Test
    fun `emit validation and do not fetch if username is incorrect`() = runTest {
      coEvery {
        mockValidator.validateUsername(any())
      } returns UsernameValidationResult.INCORRECT_EMPTY_TEXT

      coEvery {
        mockValidator.validatePassword(any())
      } returns PasswordValidationResult.CORRECT

      viewModel.onClickNext("url", true, "username", "password")
      runCurrent()

      coVerify(exactly = 0) {
        projectRepo.fetchRepo(any(), any(), any())
      }

      Assertions.assertEquals(
        UsernameValidationResult.INCORRECT_EMPTY_TEXT,
        viewModel.usernameValidationResult.getOrAwaitValue()
      )
    }

    @Test
    fun `emit validation and do not fetch if password is incorrect`() = runTest {
      coEvery {
        mockValidator.validatePassword(any())
      } returns PasswordValidationResult.INCORRECT_EMPTY_TEXT

      coEvery {
        mockValidator.validateUsername(any())
      } returns UsernameValidationResult.CORRECT

      viewModel.onClickNext("url", true, "username", "password")
      runCurrent()

      coVerify(exactly = 0) {
        projectRepo.fetchRepo(any(), any(), any())
      }

      Assertions.assertEquals(
        PasswordValidationResult.INCORRECT_EMPTY_TEXT,
        viewModel.passwordValidationResult.value
      )
    }
  }

  @ParameterizedTest()
  @EnumSource(
    value = FeedUrlValidationResult::class,
    names = ["INCORRECT_EMPTY_TEXT", "INCORRECT_INVALID_URL"],
  )
  fun `emit validation and do not fetch if feed url is incorrect`(validationResult: FeedUrlValidationResult) =
    runTest {
      coEvery {
        mockValidator.validateUsername(any())
      } returns UsernameValidationResult.CORRECT
      coEvery {
        mockValidator.validatePassword(any())
      } returns PasswordValidationResult.CORRECT
      coEvery {
        mockValidator.validateFeedUrl(any())
      } returns validationResult

      viewModel.onClickNext("url", true, "username", "password")

      runCurrent()

      coVerify(exactly = 0) {
        projectRepo.fetchRepo(any())
      }
    }

  @Test
  fun testSaveProjectOnSelectProject() = runTest {
    val project = ProjectBuilder.buildProject(-1L)

    viewModel.onSelectProject(project)

    runCurrent()

    coVerify(exactly = 1) {
      projectRepo.saveProject(project)
    }
  }
}