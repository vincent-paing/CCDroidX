package dev.aungkyawpaing.ccdroidx.feature.add

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import dev.aungkyawpaing.ccdroidx.CCDroidXDb
import dev.aungkyawpaing.ccdroidx.CoroutineTestRule
import dev.aungkyawpaing.ccdroidx._testhelper_.ProjectBuilder
import dev.aungkyawpaing.ccdroidx.api.FetchProject
import dev.aungkyawpaing.ccdroidx.data.ProjectRepo
import dev.aungkyawpaing.ccdroidx.db.projectTableAdapter
import dev.aungkyawpaing.ccdroidx.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AddProjectViewModelTest {

  @get:Rule
  var coroutineTestRule = CoroutineTestRule()

  @Rule
  @JvmField
  var instantTaskExecutorRule = InstantTaskExecutorRule()

  private val fetchProject = mockk<FetchProject>()
  private val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
  private val projectRepo = ProjectRepo(
    fetchProject,
    CCDroidXDb(driver, projectTableAdapter),
    coroutineTestRule.testDispatcherProvider
  )

  private val viewModel =
    AddProjectViewModel(projectRepo, coroutineTestRule.testDispatcherProvider)

  @Before
  fun setUp() {
    CCDroidXDb.Schema.create(driver)
    MockKAnnotations.init(this, relaxUnitFun = true)
  }

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

    viewModel.getProjectsFromFeed(url)

    runCurrent()

    Assert.assertEquals(expectedProjectList, viewModel.projectListLiveEvent.getOrAwaitValue())
  }

  @Test
  fun testOnSelectProject() = runTest {
    val project = ProjectBuilder.buildProject()

    viewModel.onSelectProject(project)

    runCurrent()

    val actual = projectRepo.getAll().first()

    Assert.assertEquals(listOf(project), actual)
  }
}