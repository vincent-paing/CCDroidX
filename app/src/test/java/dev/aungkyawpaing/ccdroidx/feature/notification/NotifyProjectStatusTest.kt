package dev.aungkyawpaing.ccdroidx.feature.notification

import dev.aungkyawpaing.ccdroidx._testhelper_.ProjectBuilder.buildProject
import dev.aungkyawpaing.ccdroidx.data.BuildStatus
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

class NotifyProjectStatusTest {

  val notificationManager: NotificationManager = mockk(relaxed = true)

  val notifyProjectStatus = NotifyProjectStatus(notificationManager)

  @Test
  fun testNotifyOnProjectBeforeSuccessAndNowFail() {
    val name = "Test Project"
    val webUrl = "https://www.test.com"
    val lastBuildTime = ZonedDateTime.ofInstant(Instant.ofEpochSecond(1000), ZoneId.of("UTC"))

    notifyProjectStatus.notify(
      previous = buildProject(
        lastBuildStatus = BuildStatus.SUCCESS,
        lastBuildTime = lastBuildTime
      ),
      now = buildProject(
        name = name,
        lastBuildStatus = BuildStatus.FAILURE,
        lastBuildTime = lastBuildTime.plusHours(1L),
        webUrl = webUrl,
      )
    )

    verify(exactly = 1) {
      notificationManager.notifyProjectFail(name, webUrl)
    }

    confirmVerified(notificationManager)
  }

  @Test
  fun testNotifyOnProjectBeforeFailAndNowFailWithNewerBuildTime() {
    val name = "Test Project"
    val webUrl = "https://www.test.com"
    val lastBuildTime = ZonedDateTime.ofInstant(Instant.ofEpochSecond(1000), ZoneId.of("UTC"))

    notifyProjectStatus.notify(
      previous = buildProject(
        lastBuildStatus = BuildStatus.FAILURE,
        lastBuildTime = lastBuildTime
      ),
      now = buildProject(
        name = name,
        lastBuildStatus = BuildStatus.FAILURE,
        webUrl = webUrl,
        lastBuildTime = lastBuildTime.plusHours(1)
      )
    )

    verify(exactly = 1) {
      notificationManager.notifyProjectFail(name, webUrl)
    }

    confirmVerified(notificationManager)
  }

  @Test
  fun testDoesNotNotifyOnProjectBeforeFailAndNowFailWithSameBuildTime() {
    val name = "Test Project"
    val webUrl = "https://www.test.com"
    val lastBuildTime = ZonedDateTime.ofInstant(Instant.ofEpochSecond(1000), ZoneId.of("UTC"))

    notifyProjectStatus.notify(
      previous = buildProject(
        lastBuildStatus = BuildStatus.FAILURE,
        lastBuildTime = lastBuildTime
      ),
      now = buildProject(
        name = name,
        lastBuildStatus = BuildStatus.FAILURE,
        webUrl = webUrl,
        lastBuildTime = lastBuildTime
      )
    )

    verify(exactly = 0) {
      notificationManager.notifyProjectFail(name, webUrl)
    }

    confirmVerified(notificationManager)
  }

  @Test
  fun testNotifyOnProjectBeforeFailAndNowSuccess() {
    val name = "Test Project"
    val webUrl = "https://www.test.com"

    notifyProjectStatus.notify(
      previous = buildProject(
        lastBuildStatus = BuildStatus.FAILURE,
      ),
      now = buildProject(
        name = name,
        lastBuildStatus = BuildStatus.SUCCESS,
        webUrl = webUrl
      )
    )

    verify(exactly = 1) {
      notificationManager.notifyProjectSuccessAfterFail(name, webUrl)
    }

    confirmVerified(notificationManager)
  }

  @Test
  fun testDoesNotNotifyOnProjectBeforeSuccessAndNowSuccess() {
    val name = "Test Project"
    val webUrl = "https://www.test.com"
    val lastBuildTime = ZonedDateTime.ofInstant(Instant.ofEpochSecond(1000), ZoneId.of("UTC"))

    notifyProjectStatus.notify(
      previous = buildProject(
        lastBuildStatus = BuildStatus.SUCCESS,
        lastBuildTime = lastBuildTime
      ),
      now = buildProject(
        name = name,
        lastBuildStatus = BuildStatus.SUCCESS,
        lastBuildTime = lastBuildTime.plusHours(1L),
        webUrl = webUrl
      )
    )

    verify(exactly = 0) {
      notificationManager.notifyProjectFail(any(), any())
      notificationManager.notifyProjectSuccessAfterFail(any(), any())
    }

    confirmVerified(notificationManager)
  }
}
