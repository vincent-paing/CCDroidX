package dev.aungkyawpaing.ccdroidx.data.api

import dev.aungkyawpaing.ccdroidx.common.BuildState
import dev.aungkyawpaing.ccdroidx.common.BuildStatus
import okhttp3.Response
import org.simpleframework.xml.core.Persister
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoField

object CCTrayParser {

  private fun parseDateTime(dateTimeString: String): ZonedDateTime {
    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(
      "[yyyy-MM-dd'T'HH:mm:ss.nXXX]" // ISO_ZONED_DATE_TIME
          + "[yyyy-MM-dd'T'HH:mm:ss.SSSxx]" // TRAVIS_FORMAT
          + "[yyyy-MM-dd'T'HH:mm:ssXXX]" // BUILD_KITE_FORMAT
    )
    return ZonedDateTime.parse(dateTimeString, formatter).with(ChronoField.MILLI_OF_SECOND, 0)
  }

  fun parseResponse(response: Response): List<ProjectResponse> {
    val serializer = Persister()
    val data = serializer.read(CCTrayProjects::class.java, response.body.byteStream())
    return data.project?.map {
      ProjectResponse(
        name = it.name!!,
        activity = when (it.activity) {
          "Sleeping" -> BuildState.SLEEPING
          "Building" -> BuildState.BUILDING
          "CheckingModifications" -> BuildState.CHECKING_MODIFICATIONS
          else -> throw IllegalArgumentException()
        },
        lastBuildStatus = when (it.lastBuildStatus) {
          "Success" -> BuildStatus.SUCCESS
          "Failure" -> BuildStatus.FAILURE
          "Exception" -> BuildStatus.EXCEPTION
          else -> BuildStatus.UNKNOWN
        },
        lastBuildLabel = it.lastBuildLabel,
        lastBuildTime = parseDateTime(it.lastBuildTime!!),
        nextBuildTime = it.nextBuildTime?.let { nextBuildTime -> parseDateTime(nextBuildTime) },
        webUrl = it.webUrl!!,
        feedUrl = response.request.url.toUrl().toString(),
      )
    } ?: emptyList()
  }
}