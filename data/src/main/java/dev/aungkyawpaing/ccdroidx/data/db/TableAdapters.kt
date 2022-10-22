package dev.aungkyawpaing.ccdroidx.data.db

import com.squareup.sqldelight.EnumColumnAdapter
import dev.aungkyawpaing.ccdroidx.ProjectTable

val projectTableAdapter = ProjectTable.Adapter(
  activityAdapter = EnumColumnAdapter(),
  lastBuildStatusAdapter = BuildStatusColumnAdapter,
  lastBuildTimeAdapter = ZonedDateTimeColumnAdapter,
  nextBuildTimeAdapter = ZonedDateTimeColumnAdapter,
  mutedUntilAdapter = ZonedDateTimeColumnAdapter
)