package dev.aungkyawpaing.ccdroidx.data.db

import com.squareup.sqldelight.ColumnAdapter
import java.time.*

object ZonedDateTimeColumnAdapter : ColumnAdapter<ZonedDateTime, Long> {

  override fun decode(databaseValue: Long): ZonedDateTime {
    return Instant.ofEpochMilli(databaseValue).atZone(ZoneId.of("UTC"))
  }

  override fun encode(value: ZonedDateTime): Long {
    return value.toInstant().toEpochMilli()
  }
}