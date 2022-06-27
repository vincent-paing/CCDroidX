package dev.aungkyawpaing.ccdroidx.data

import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import dev.aungkyawpaing.ccdroidx.CCDroidXDb
import dev.aungkyawpaing.ccdroidx._testhelper_.CoroutineTest
import dev.aungkyawpaing.ccdroidx._testhelper_.ProjectBuilder.buildProject
import dev.aungkyawpaing.ccdroidx.api.FetchProject
import dev.aungkyawpaing.ccdroidx.api.NetworkException
import dev.aungkyawpaing.ccdroidx.api.ProjectResponse
import dev.aungkyawpaing.ccdroidx.db.projectTableAdapter
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.*
import java.time.ZoneId
import java.time.ZonedDateTime

@OptIn(ExperimentalCoroutinesApi::class)
class ProjectRepoTest : CoroutineTest() {

  private val fetchProject = mockk<FetchProject>()
  private val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
  private val db = CCDroidXDb(driver, projectTableAdapter)

  private val projectRepo = ProjectRepo(
    fetchProject,
    db,
    testDispatcherProvider
  )

  @BeforeEach
  fun setUp() {
    CCDroidXDb.Schema.create(driver)
  }

  @Nested
  @DisplayName("fetchRepo")
  internal inner class FetchRepoFunction {

    @Test
    fun `invoke fetch and map response`() = runTest {
      val url = "url"

      coEvery {
        fetchProject.requestForProjectList(url)
      } returns listOf(
        ProjectResponse(
          name = "Project Name",
          activity = BuildState.SLEEPING,
          lastBuildStatus = BuildStatus.SUCCESS,
          lastBuildLabel = "Last Build Label",
          lastBuildTime = ZonedDateTime.of(2022, 4, 21, 0, 0, 0, 0, ZoneId.of("UTC")),
          nextBuildTime = ZonedDateTime.of(2022, 4, 22, 0, 0, 0, 0, ZoneId.of("UTC")),
          webUrl = "https://example.com/master",
          feedUrl = "https://www.example.com/cc.xml",
        )
      )

      val expected = listOf(
        Project(
          id = -1L,
          name = "Project Name",
          activity = BuildState.SLEEPING,
          lastBuildStatus = BuildStatus.SUCCESS,
          lastBuildLabel = "Last Build Label",
          lastBuildTime = ZonedDateTime.of(2022, 4, 21, 0, 0, 0, 0, ZoneId.of("UTC")),
          nextBuildTime = ZonedDateTime.of(2022, 4, 22, 0, 0, 0, 0, ZoneId.of("UTC")),
          webUrl = "https://example.com/master",
          feedUrl = "https://www.example.com/cc.xml",
          isMuted = false,
          mutedUntil = null
        )
      )

      val actual = projectRepo.fetchRepo(url)

      Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `pipe exception thrown from fetch repo`() = runTest {
      val execption = NetworkException()
      coEvery {
        fetchProject.requestForProjectList(any())
      } throws execption

      val result = kotlin.runCatching {
        projectRepo.fetchRepo("url")
      }.onFailure { actual ->
        Assertions.assertEquals(execption, actual)
      }

      Assertions.assertTrue(result.isFailure)
    }
  }

  @Nested
  @DisplayName("saveProject")
  internal inner class SaveProject {

    @Test
    fun `insert if id is -1L`() = runTest {
      val project = buildProject(id = -1L)

      projectRepo.saveProject(project)

      Assertions.assertEquals(listOf(project.copy(id = 1)), projectRepo.getAll().first())
    }

    @Test
    fun `update if id is not -1L`() = runTest {
      val project = buildProject(id = -1L)
      projectRepo.saveProject(project)

      val updateProject = project.copy(id = 1, name = "Updated name")
      projectRepo.saveProject(updateProject)
      Assertions.assertEquals(listOf(updateProject), projectRepo.getAll().first())
    }
  }

  @Nested
  @DisplayName("delete")
  internal inner class Delete {

    @Test
    fun `delete from db`() = runTest {
      val project = buildProject(id = -1L)

      projectRepo.saveProject(project)

      projectRepo.delete(1)

      Assertions.assertEquals(emptyList<Project>(), projectRepo.getAll().first())
    }
  }

  @Nested
  @DisplayName("unmuteProject")
  internal inner class UnmuteProject {

    @Test
    fun `updated isMuted to false`() = runTest {
      val project = buildProject(id = 1)

      db.projectTableQueries.insert(
        name = project.name,
        activity = project.activity,
        lastBuildStatus = project.lastBuildStatus,
        lastBuildLabel = project.lastBuildLabel,
        lastBuildTime = project.lastBuildTime,
        nextBuildTime = project.nextBuildTime,
        webUrl = project.webUrl,
        feedUrl = project.feedUrl,
      )
      db.projectTableQueries.updateMute(true, null, project.id)

      projectRepo.unmuteProject(project.id)

      Assertions.assertEquals(
        listOf(
          project.copy(
            isMuted = false
          )
        ), projectRepo.getAll().first()
      )
    }
  }

  @Nested
  @DisplayName("muteProject")
  internal inner class MuteProject {

    @Test
    fun `updated isMuted to true`() = runTest {
      val project = buildProject(id = 1)

      db.projectTableQueries.insert(
        name = project.name,
        activity = project.activity,
        lastBuildStatus = project.lastBuildStatus,
        lastBuildLabel = project.lastBuildLabel,
        lastBuildTime = project.lastBuildTime,
        nextBuildTime = project.nextBuildTime,
        webUrl = project.webUrl,
        feedUrl = project.feedUrl,
      )
      db.projectTableQueries.updateMute(false, null, project.id)

      projectRepo.muteProject(project.id)

      Assertions.assertEquals(
        listOf(
          project.copy(
            isMuted = true
          )
        ), projectRepo.getAll().first()
      )
    }
  }
}