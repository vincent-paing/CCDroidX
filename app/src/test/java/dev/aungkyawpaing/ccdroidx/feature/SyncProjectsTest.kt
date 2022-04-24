package dev.aungkyawpaing.ccdroidx.feature

import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import dev.aungkyawpaing.ccdroidx.CCDroidXDb
import dev.aungkyawpaing.ccdroidx.CoroutineTestRule
import dev.aungkyawpaing.ccdroidx._testhelper_.ProjectBuilder.buildProject
import dev.aungkyawpaing.ccdroidx.api.FetchProject
import dev.aungkyawpaing.ccdroidx.data.BuildState
import dev.aungkyawpaing.ccdroidx.data.BuildStatus
import dev.aungkyawpaing.ccdroidx.data.Project
import dev.aungkyawpaing.ccdroidx.data.ProjectRepo
import dev.aungkyawpaing.ccdroidx.db.projectTableAdapter
import dev.aungkyawpaing.ccdroidx.feature.sync.SyncProjects
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime

class SyncProjectsTest {

  @get:Rule
  var coroutineTestRule = CoroutineTestRule()

  private val fetchProject = mockk<FetchProject>()
  private val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
  private val projectRepo = ProjectRepo(
    fetchProject,
    CCDroidXDb(driver, projectTableAdapter),
    coroutineTestRule.testDispatcherProvider
  )
  private val syncProject = SyncProjects(
    projectRepo
  )

  @Before
  fun setUp() {
    CCDroidXDb.Schema.create(driver)
    MockKAnnotations.init(this, relaxUnitFun = true)
  }


  @Test
  fun testSyncProject() = coroutineTestRule.scope.runTest {
    val savedProjectOne = buildProject()
    projectRepo.saveProject(savedProjectOne)
    val savedProjectTwo = buildProject().copy(feedUrl = "diffFeed")
    projectRepo.saveProject(savedProjectTwo)

    val updatedProjectOne = savedProjectOne.copy(
      name = "updated"
    )
    val updatedProjectTwo = savedProjectTwo.copy(
      name = "updated two"
    )

    coEvery {
      fetchProject.requestForProjectList(savedProjectOne.feedUrl)
    } returns listOf(updatedProjectOne)

    coEvery {
      fetchProject.requestForProjectList(savedProjectTwo.feedUrl)
    } returns listOf(updatedProjectTwo)

    syncProject.sync()

    val actual = projectRepo.getAll().first()

    Assert.assertEquals(
      listOf(
       updatedProjectOne,
        updatedProjectTwo
      ), actual
    )

  }
}