package dev.aungkyawpaing.ccdroidx.feature.settings.syncinterval

import org.junit.Assert
import org.junit.Test
import java.time.Duration

class SyncIntervalTest {

  @Test
  fun testSyncIntervalAsDurationMinutes() {
    val syncInterval = SyncInterval(15, SyncIntervalTimeUnit.MINUTES)
    val expected = Duration.ofMinutes(15L)
    Assert.assertEquals(expected, syncInterval.asDuration())
  }

  @Test
  fun testSyncIntervalAsDurationHours() {
    val syncInterval = SyncInterval(20, SyncIntervalTimeUnit.HOUR)
    val expected = Duration.ofHours(20L)
    Assert.assertEquals(expected, syncInterval.asDuration())
  }

  @Test
  fun testSyncIntervalAsDuration() {
    val syncInterval = SyncInterval(99, SyncIntervalTimeUnit.DAY)
    val expected = Duration.ofDays(99L)
    Assert.assertEquals(expected, syncInterval.asDuration())
  }
}