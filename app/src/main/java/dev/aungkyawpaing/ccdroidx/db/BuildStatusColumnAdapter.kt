package dev.aungkyawpaing.ccdroidx.db

import com.squareup.sqldelight.ColumnAdapter
import dev.aungkyawpaing.ccdroidx.data.BuildStatus

object BuildStatusColumnAdapter : ColumnAdapter<BuildStatus, Long> {

  override fun decode(databaseValue: Long): BuildStatus {
    return when (databaseValue) {
      1L -> BuildStatus.SUCCESS
      3L -> BuildStatus.EXCEPTION
      4L -> BuildStatus.FAILURE
      else -> BuildStatus.UNKNOWN
    }

  }

  override fun encode(value: BuildStatus): Long {
    return when (value) {
      BuildStatus.SUCCESS -> 1
      BuildStatus.UNKNOWN -> 2
      BuildStatus.EXCEPTION -> 3
      BuildStatus.FAILURE -> 4
    }
  }


}