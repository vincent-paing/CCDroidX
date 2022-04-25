package dev.aungkyawpaing.ccdroidx.data

import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import dev.aungkyawpaing.ccdroidx.CCDroidXDb
import dev.aungkyawpaing.ccdroidx.CoroutineTestRule
import dev.aungkyawpaing.ccdroidx._testhelper_.ProjectBuilder.buildProject
import dev.aungkyawpaing.ccdroidx.api.FetchProject
import dev.aungkyawpaing.ccdroidx.db.projectTableAdapter
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProjectRepoTest {

  @get:Rule
  var coroutineTestRule = CoroutineTestRule()

  private val fetchProject = mockk<FetchProject>()
  private val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)

  private val projectRepo = ProjectRepo(
    fetchProject,
    CCDroidXDb(driver, projectTableAdapter),
    coroutineTestRule.testDispatcherProvider
  )

  @Before
  fun setUp() {
    CCDroidXDb.Schema.create(driver)
  }


  @Test
  fun testSaveProject() = coroutineTestRule.scope.runTest {
    val project = buildProject(id = -1L)

    projectRepo.saveProject(project)

    val savedProjects = projectRepo.getAll().first()
    Assert.assertEquals(listOf(project.copy(id = 1)), savedProjects)


    val updatedProject = savedProjects[0].copy(name = "updated project")
    projectRepo.saveProject(updatedProject)

    Assert.assertEquals(listOf(updatedProject), projectRepo.getAll().first())

  }
}