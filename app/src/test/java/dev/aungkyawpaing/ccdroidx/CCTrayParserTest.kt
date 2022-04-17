package dev.aungkyawpaing.ccdroidx

import dev.aungkyawpaing.ccdroidx.data.BuildState
import dev.aungkyawpaing.ccdroidx.data.BuildStatus
import dev.aungkyawpaing.ccdroidx.data.CCTrayParser
import dev.aungkyawpaing.ccdroidx.data.Project
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Test
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime


class CCTrayParserTest {

  @Test
  fun testParseSleepingActivityAndExceptionStatus() {
    val input = """
          <Projects>
              <Project
                  name="SvnTest"
                  activity="Sleeping"
                  lastBuildStatus="Exception"
                  lastBuildLabel="8"
                  lastBuildTime="2005-09-28T10:30:34.6362160+01:00"
                  nextBuildTime="2005-10-04T14:31:52.4509248+01:00"
                  webUrl="http://mrtickle/ccnet/"/>

          </Projects>
        """.trimIndent().toResponseBody("application/xml".toMediaType())

    val expected = listOf(
      Project(
        name = "SvnTest",
        activity = BuildState.SLEEPING,
        lastBuildStatus = BuildStatus.EXCEPTION,
        lastBuildLabel = "8",
        lastBuildTime = ZonedDateTime.of(
          2005, 9, 28, 10, 30, 34, 636216000,
          ZoneId.ofOffset("", ZoneOffset.of("+01:00"))
        ),
        nextBuildTime = ZonedDateTime.of(
          2005, 10, 4, 14, 31, 52, 450924800,
          ZoneId.ofOffset("", ZoneOffset.of("+01:00"))
        ),
        webUrl = "http://mrtickle/ccnet/"
      )
    )

    val actual = CCTrayParser.parseResponse(input)
    Assert.assertEquals(expected, actual)
  }

  @Test
  fun testParseBuildingActivityAndSuccessStatus() {
    val input = """
          <Projects>
              <Project
                  name="SvnTest"
                  activity="Building"
                  lastBuildStatus="Success"
                  lastBuildLabel="8"
                  lastBuildTime="2005-09-28T10:30:34.6362160+01:00"
                  nextBuildTime="2005-10-04T14:31:52.4509248+01:00"
                  webUrl="http://mrtickle/ccnet/"/>

          </Projects>
        """.trimIndent().toResponseBody("application/xml".toMediaType())

    val expected = listOf(
      Project(
        name = "SvnTest",
        activity = BuildState.BUILDING,
        lastBuildStatus = BuildStatus.SUCCESS,
        lastBuildLabel = "8",
        lastBuildTime = ZonedDateTime.of(
          2005, 9, 28, 10, 30, 34, 636216000,
          ZoneId.ofOffset("", ZoneOffset.of("+01:00"))
        ),
        nextBuildTime = ZonedDateTime.of(
          2005, 10, 4, 14, 31, 52, 450924800,
          ZoneId.ofOffset("", ZoneOffset.of("+01:00"))
        ),
        webUrl = "http://mrtickle/ccnet/"
      )
    )

    val actual = CCTrayParser.parseResponse(input)
    Assert.assertEquals(expected, actual)
  }

  @Test
  fun testParseCheckingModificationsActivityAndFailureStatus() {
    val input = """
          <Projects>
              <Project
                  name="SvnTest"
                  activity="CheckingModifications"
                  lastBuildStatus="Failure"
                  lastBuildLabel="8"
                  lastBuildTime="2005-09-28T10:30:34.6362160+01:00"
                  nextBuildTime="2005-10-04T14:31:52.4509248+01:00"
                  webUrl="http://mrtickle/ccnet/"/>

          </Projects>
        """.trimIndent().toResponseBody("application/xml".toMediaType())

    val expected = listOf(
      Project(
        name = "SvnTest",
        activity = BuildState.CHECKING_MODIFICATIONS,
        lastBuildStatus = BuildStatus.FAILURE,
        lastBuildLabel = "8",
        lastBuildTime = ZonedDateTime.of(
          2005, 9, 28, 10, 30, 34, 636216000,
          ZoneId.ofOffset("", ZoneOffset.of("+01:00"))
        ),
        nextBuildTime = ZonedDateTime.of(
          2005, 10, 4, 14, 31, 52, 450924800,
          ZoneId.ofOffset("", ZoneOffset.of("+01:00"))
        ),
        webUrl = "http://mrtickle/ccnet/"
      )
    )

    val actual = CCTrayParser.parseResponse(input)
    Assert.assertEquals(expected, actual)
  }

  @Test
  fun testParseUnknownStatus() {
    val input = """
          <Projects>
              <Project
                  name="SvnTest"
                  activity="CheckingModifications"
                  lastBuildStatus="Unknown"
                  lastBuildLabel="8"
                  lastBuildTime="2005-09-28T10:30:34.6362160+01:00"
                  nextBuildTime="2005-10-04T14:31:52.4509248+01:00"
                  webUrl="http://mrtickle/ccnet/"/>

          </Projects>
        """.trimIndent().toResponseBody("application/xml".toMediaType())

    val expected = listOf(
      Project(
        name = "SvnTest",
        activity = BuildState.CHECKING_MODIFICATIONS,
        lastBuildStatus = BuildStatus.UNKNOWN,
        lastBuildLabel = "8",
        lastBuildTime = ZonedDateTime.of(
          2005, 9, 28, 10, 30, 34, 636216000,
          ZoneId.ofOffset("", ZoneOffset.of("+01:00"))
        ),
        nextBuildTime = ZonedDateTime.of(
          2005, 10, 4, 14, 31, 52, 450924800,
          ZoneId.ofOffset("", ZoneOffset.of("+01:00"))
        ),
        webUrl = "http://mrtickle/ccnet/"
      )
    )

    val actual = CCTrayParser.parseResponse(input)
    Assert.assertEquals(expected, actual)
  }

  @Test
  fun testOptionalLabelAndNextBuildTime() {
    val input = """
          <Projects>
              <Project
                  name="SvnTest"
                  activity="CheckingModifications"
                  lastBuildStatus="Failure"
                  lastBuildTime="2005-09-28T10:30:34.6362160+01:00"
                  webUrl="http://mrtickle/ccnet/"/>

          </Projects>
        """.trimIndent().toResponseBody("application/xml".toMediaType())

    val expected = listOf(
      Project(
        name = "SvnTest",
        activity = BuildState.CHECKING_MODIFICATIONS,
        lastBuildStatus = BuildStatus.FAILURE,
        lastBuildLabel = null,
        lastBuildTime = ZonedDateTime.of(
          2005, 9, 28, 10, 30, 34, 636216000,
          ZoneId.ofOffset("", ZoneOffset.of("+01:00"))
        ),
        nextBuildTime = null,
        webUrl = "http://mrtickle/ccnet/"
      )
    )

    val actual = CCTrayParser.parseResponse(input)
    Assert.assertEquals(expected, actual)
  }
}