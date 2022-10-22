package dev.aungkyawpaing.ccdroidx.feature

import dev.aungkyawpaing.ccdroidx._testhelper_.CoroutineTest
import dev.aungkyawpaing.ccdroidx._testhelper_.ProjectBuilder
import dev.aungkyawpaing.ccdroidx.data.api.NetworkException
import dev.aungkyawpaing.ccdroidx.data.Project
import dev.aungkyawpaing.ccdroidx.data.ProjectRepo
import dev.aungkyawpaing.ccdroidx.feature.sync.LastSyncedState
import dev.aungkyawpaing.ccdroidx.feature.sync.LastSyncedStatus
import dev.aungkyawpaing.ccdroidx.feature.sync.SyncMetaDataStorage
import dev.aungkyawpaing.ccdroidx.feature.sync.SyncProjects
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.Clock
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

@OptIn(ExperimentalCoroutinesApi::class)
class SyncProjectsTest : CoroutineTest() {

  private val clock: Clock = Clock.fixed(Instant.ofEpochSecond(6000), ZoneId.of("UTC"))
  private val syncMetaDataStorage = mockk<SyncMetaDataStorage>(relaxed = true)

  private val projectRepo = mockk<ProjectRepo>(relaxUnitFun = true)
  private val syncProject = SyncProjects(
    projectRepo,
    syncMetaDataStorage,
    clock
  )

  @Test
  fun `save project on success`() = runTest {
    val feedUrl = "feedUrl"

    val projectOne =
      ProjectBuilder.buildProject()
        .copy(id = 1L, name = "Project One", feedUrl = feedUrl, lastBuildLabel = "A")
    val projectTwo =
      ProjectBuilder.buildProject()
        .copy(id = 2L, name = "Project Two", feedUrl = feedUrl, lastBuildLabel = "D")

    val updatedProjectOne = projectOne.copy(id = -1L, name = "Project One", lastBuildLabel = "ABC")
    val updatedProjectTwo = projectOne.copy(id = -1L, name = "Project Two", lastBuildLabel = "DEF")

    coEvery {
      projectRepo.getAll()
    } returns flow { emit(listOf(projectOne, projectTwo)) }

    coEvery {
      projectRepo.fetchRepo(
        feedUrl, projectOne.authentication!!.username,
        projectOne.authentication!!.password
      )
    } returns listOf(
      updatedProjectOne,
      updatedProjectTwo
    )

    syncProject.sync(mockk(relaxed = true))


    coVerify(exactly = 1) {
      projectRepo.saveProject(updatedProjectOne.copy(id = projectOne.id))
      projectRepo.saveProject(updatedProjectTwo.copy(id = projectTwo.id))
    }
  }

  @Test
  fun `invoke onProjectSynced on success`() = runTest {
    val feedUrl = "feedUrl"
    val projectOne =
      ProjectBuilder.buildProject()
        .copy(id = 1L, name = "Project One", feedUrl = feedUrl, lastBuildLabel = "A")
    val projectTwo =
      ProjectBuilder.buildProject()
        .copy(id = 2L, name = "Project Two", feedUrl = feedUrl, lastBuildLabel = "D")

    val updatedProjectOne = projectOne.copy(id = -1L, name = "Project One", lastBuildLabel = "ABC")
    val updatedProjectTwo = projectOne.copy(id = -1L, name = "Project Two", lastBuildLabel = "DEF")

    coEvery {
      projectRepo.getAll()
    } returns flow { emit(listOf(projectOne, projectTwo)) }

    coEvery {
      projectRepo.fetchRepo(feedUrl, any(), any())
    } returns listOf(
      updatedProjectOne,
      updatedProjectTwo
    )

    val mockOnProjectSynced = mockk<(Project, Project) -> Unit>(relaxed = true)
    syncProject.sync(mockOnProjectSynced)

    coVerify(exactly = 1) {
      mockOnProjectSynced(projectOne, updatedProjectOne.copy(id = projectOne.id))
      mockOnProjectSynced(projectTwo, updatedProjectTwo.copy(id = projectTwo.id))

    }
  }

  @Test
  fun `pipe Error on Sync Fail`() = runTest {
    val exception = NetworkException()

    coEvery {
      projectRepo.getAll()
    } returns flow { emit(listOf(ProjectBuilder.buildProject())) }

    coEvery {
      projectRepo.fetchRepo(any(), any(), any())
    } throws exception

    val result = kotlin.runCatching {
      syncProject.sync(mockk(relaxed = true))
    }.onFailure { actual ->
      Assertions.assertEquals(exception, actual)
    }

    Assertions.assertTrue(result.isFailure)
  }

  @Test
  fun `update SyncMetaDataStorage on Success`() = runTest {
    val projectList = listOf(ProjectBuilder.buildProject())

    coEvery {
      projectRepo.getAll()
    } returns flow { emit(projectList) }

    coEvery {
      projectRepo.fetchRepo(any(), any(), any())
    } returns projectList

    syncProject.sync(mockk(relaxed = true))

    coVerifyOrder {
      syncMetaDataStorage.saveLastSyncedTime(
        LastSyncedStatus(
          ZonedDateTime.now(clock),
          LastSyncedState.SYNCING
        )
      )
      syncMetaDataStorage.saveLastSyncedTime(
        LastSyncedStatus(
          ZonedDateTime.now(clock),
          LastSyncedState.SUCCESS
        )
      )
    }
  }

  @Test
  fun `update SyncMetaDataStorage on sync fail`() = runTest {
    coEvery {
      projectRepo.getAll()
    } returns flow { emit(listOf(ProjectBuilder.buildProject())) }

    coEvery {
      projectRepo.fetchRepo(any(), any(), any())
    } throws NetworkException()

    kotlin.runCatching {
      syncProject.sync(mockk(relaxed = true))
    }

    coVerifyOrder {
      syncMetaDataStorage.saveLastSyncedTime(
        LastSyncedStatus(
          ZonedDateTime.now(clock),
          LastSyncedState.SYNCING
        )
      )
      syncMetaDataStorage.saveLastSyncedTime(
        LastSyncedStatus(
          ZonedDateTime.now(clock),
          LastSyncedState.FAILED,
          1
        )
      )
    }
  }
}
