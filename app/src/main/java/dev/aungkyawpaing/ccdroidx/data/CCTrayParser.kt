package dev.aungkyawpaing.ccdroidx.data

import okhttp3.ResponseBody
import org.simpleframework.xml.core.Persister
import java.lang.IllegalArgumentException
import java.time.ZonedDateTime

object CCTrayParser {

  fun parseResponse(body: ResponseBody): List<Project> {
    val serializer = Persister()
    val data = serializer.read(CCTrayProjects::class.java, body.charStream())
    return data.project?.map {
      Project(
        name = it.name!!,
        activity = when (it.activity) {
          "Sleeping" -> BuildState.SLEEPING
          "BUILDING" -> BuildState.BUILDING
          "CHECKING_MODIFICATIONS" -> BuildState.CHECKING_MODIFICATIONS
          else -> throw IllegalArgumentException()
        },
        lastBuildStatus = when (it.lastBuildStatus) {
          "Success" -> BuildStatus.SUCCESS
          "Failure" -> BuildStatus.FAILURE
          "Exception" -> BuildStatus.EXCEPTION
          else -> BuildStatus.UNKNOWN
        },
        lastBuildLabel = it.lastBuildLabel,
        lastBuildTime = ZonedDateTime.parse(it.lastBuildTime!!),
        nextBuildTime = if (it.nextBuildTime != null) ZonedDateTime.parse(it.nextBuildTime) else null,
        webUrl = it.webUrl!!
      )
    } ?: emptyList()
  }
}