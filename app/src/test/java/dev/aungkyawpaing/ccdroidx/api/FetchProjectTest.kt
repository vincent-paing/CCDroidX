package dev.aungkyawpaing.ccdroidx.api

import dev.aungkyawpaing.ccdroidx.CoroutineTestRule
import dev.aungkyawpaing.ccdroidx.data.BuildState
import dev.aungkyawpaing.ccdroidx.data.BuildStatus
import dev.aungkyawpaing.ccdroidx.data.Project
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import java.time.*


@ExperimentalCoroutinesApi
class FetchProjectTest {

  @get:Rule
  var coroutineTestRule = CoroutineTestRule()

  @Test
  fun testParseResponseCorrectly() = runTest {
    val server = MockWebServer()

    server.enqueue(
      MockResponse()
        .setResponseCode(200)
        .setBody(
          """
      <Projects>
          <Project 
              name="circleci/ex"
              activity="Sleeping"
              webUrl="https://circleci.com/gh/circleci/ex/tree/main"
              lastBuildLabel="2730"
              lastBuildStatus="Success"
              lastBuildTime="2022-04-18T19:29:37.845Z"/>
      </Projects>
        """
        )
    )

    server.start()

    val url = server.url("").toString()
    val fetchProject = FetchProject(OkHttpClient())

    val actual = fetchProject.requestForProjectList(url, null, null)

    val expected = listOf(
      ProjectResponse(
        name = "circleci/ex",
        activity = BuildState.SLEEPING,
        lastBuildStatus = BuildStatus.SUCCESS,
        lastBuildLabel = "2730",
        lastBuildTime = ZonedDateTime.of(
          2022, 4, 18, 19, 29, 37, 0,
          ZoneId.ofOffset("", ZoneOffset.of("+00:00"))
        ),
        nextBuildTime = null,
        webUrl = "https://circleci.com/gh/circleci/ex/tree/main",
        feedUrl = url,
      )
    )

    Assert.assertEquals(expected, actual)

    val request1 = server.takeRequest()
    Assert.assertEquals("/", request1.path)

    server.shutdown()
  }
}