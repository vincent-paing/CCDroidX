package dev.aungkyawpaing.ccdroidx.feature.projectlist

import org.ocpsoft.prettytime.PrettyTime
import java.time.ZonedDateTime

object TimeAgoFormatter {

  private val prettyTime = PrettyTime()

  @JvmStatic
  fun format(time: ZonedDateTime): String {
    return prettyTime.format(time)
  }

}