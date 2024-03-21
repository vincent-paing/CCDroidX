package dev.aungkyawpaing.ccdroidx.data.db

import androidx.room.TypeConverter
import dev.aungkyawpaing.ccdroidx.common.BuildStatus
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

internal class Converters {

  @TypeConverter
  fun longToBaseStatus(databaseValue: Long): BuildStatus {
    return when (databaseValue) {
      1L -> BuildStatus.SUCCESS
      3L -> BuildStatus.EXCEPTION
      4L -> BuildStatus.FAILURE
      else -> BuildStatus.UNKNOWN
    }

  }

  @TypeConverter
  fun buildStatusToLong(value: BuildStatus): Long {
    return when (value) {
      BuildStatus.SUCCESS -> 1
      BuildStatus.UNKNOWN -> 2
      BuildStatus.EXCEPTION -> 3
      BuildStatus.FAILURE -> 4
    }
  }

  @TypeConverter
  fun zonedDateTimeToLong(databaseValue: Long): ZonedDateTime {
    return Instant.ofEpochMilli(databaseValue).atZone(ZoneId.of("UTC"))
  }

  @TypeConverter
  fun longToZonedDateTime(value: ZonedDateTime): Long {
    return value.toInstant().toEpochMilli()
  }

}