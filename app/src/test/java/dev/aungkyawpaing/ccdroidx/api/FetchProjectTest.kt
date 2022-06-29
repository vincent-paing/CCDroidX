package dev.aungkyawpaing.ccdroidx.api

import dev.aungkyawpaing.ccdroidx._testhelper_.CoroutineTest
import dev.aungkyawpaing.ccdroidx.data.BuildState
import dev.aungkyawpaing.ccdroidx.data.BuildStatus
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime


@ExperimentalCoroutinesApi
class FetchProjectTest : CoroutineTest() {

  @Test
  fun `makes request with url`() = runTest {
    val server = MockWebServer()

    server.enqueue(
      MockResponse()
        .setResponseCode(200)
    )

    server.start()
    val fetchProject = FetchProject(OkHttpClient())

    runCatching {
      fetchProject.requestForProjectList(server.url("/cc.xml").toString(), null, null)
    }

    val request1 = server.takeRequest()
    Assertions.assertEquals("/cc.xml", request1.path)

    server.shutdown()
  }

  @Test
  fun `make request with no auth if username is null`() = runTest {
    val server = MockWebServer()

    server.enqueue(
      MockResponse()
        .setResponseCode(200)
    )

    server.start()
    val fetchProject = FetchProject(OkHttpClient())

    runCatching {
      fetchProject.requestForProjectList(server.url("/cc.xml").toString(), null, "password")
    }

    val request1 = server.takeRequest()
    Assertions.assertNull(request1.getHeader("Authorization"))

    server.shutdown()
  }

  @Test
  fun `make request with no auth if password is null`() = runTest {
    val server = MockWebServer()

    server.enqueue(
      MockResponse()
        .setResponseCode(200)
    )

    server.start()
    val fetchProject = FetchProject(OkHttpClient())

    runCatching {
      fetchProject.requestForProjectList(server.url("/cc.xml").toString(), "username", null)
    }

    val request1 = server.takeRequest()
    Assertions.assertNull(request1.getHeader("Authorization"))

    server.shutdown()
  }

  @Test
  fun `make request with auth if there is username and password`() = runTest {
    val server = MockWebServer()

    server.enqueue(
      MockResponse()
        .setResponseCode(200)
    )

    server.start()
    val fetchProject = FetchProject(OkHttpClient())

    runCatching {
      fetchProject.requestForProjectList(server.url("/cc.xml").toString(), "username", "password")
    }

    val request1 = server.takeRequest()
    val credential = Credentials.basic("username", "password")
    Assertions.assertEquals(credential, request1.getHeader("Authorization"))

    server.shutdown()
  }

  @Test
  fun `parse response Correctly`() = runTest {
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

    Assertions.assertEquals(expected, actual)
  }

}