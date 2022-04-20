package dev.aungkyawpaing.ccdroidx

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dev.aungkyawpaing.ccdroidx._testhelper_.ProjectBuilder
import dev.aungkyawpaing.ccdroidx.api.FetchProject
import dev.aungkyawpaing.ccdroidx.feature.add.AddProjectViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.yield
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddProjectViewModelTest {

  @get:Rule
  var coroutineTestRule = CoroutineTestRule()

  @Rule
  @JvmField
  var instantTaskExecutorRule = InstantTaskExecutorRule()

  private val fetchProject = mockk<FetchProject>()

  @Before
  fun setUp() =
    MockKAnnotations.init(this, relaxUnitFun = true)

  @Test
  fun testEmitValueOnSuccess() = runTest {
    val url =
      "https://api.travis-ci.com/repos/vincent-paing/myanmar-phonenumber-kt/cc.xml?branch=master"

    val expectedProjectList = listOf(
      ProjectBuilder.buildProject()
    )

    coEvery {
      fetchProject.requestForProjectList(url)
    } returns expectedProjectList

    val viewModel =
      AddProjectViewModel(fetchProject, coroutineTestRule.testDispatcherProvider)

    viewModel.projectListLiveEvent.observeForTesting {
      yield()
      viewModel.getProjectsFromFeed(url)
      runCurrent()
      Assert.assertEquals(expectedProjectList, viewModel.projectListLiveEvent.value)
    }
  }
}