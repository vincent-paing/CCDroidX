package dev.aungkyawpaing.ccdroidx.feature.settings.syncinterval

import org.junit.Assert
import org.junit.Test

class SyncIntervalValidationTest {

  private val validation = SyncIntervalValidation()

  @Test
  fun emptyTextShouldReturnIncorrect() {
    val expected = SyncIntervalValidationResult.INCORRECT_EMPTY_VALUE
    val actual = validation.validateProjectFeedUrl("", SyncIntervalTimeUnit.MINUTES)

    Assert.assertEquals(expected, actual)
  }

  @Test
  fun nullTextShouldReturnIncorrect() {
    val expected = SyncIntervalValidationResult.INCORRECT_EMPTY_VALUE
    val actual = validation.validateProjectFeedUrl(null, SyncIntervalTimeUnit.MINUTES)

    Assert.assertEquals(expected, actual)
  }

  @Test
  fun nullTimeUnitShouldReturnIncorrect() {
    val expected = SyncIntervalValidationResult.INCORRECT_EMPTY_VALUE
    val actual = validation.validateProjectFeedUrl("15", null)

    Assert.assertEquals(expected, actual)
  }

  @Test
  fun nonIntegerTextShouldReturnIncorrect() {
    val expected = SyncIntervalValidationResult.INCORRECT_NON_INTEGER
    val actual = validation.validateProjectFeedUrl("abcd", SyncIntervalTimeUnit.MINUTES)

    Assert.assertEquals(expected, actual)
  }

  @Test
  fun lessThan15MinutesShouldReturnIncorrect() {
    val expected = SyncIntervalValidationResult.INCORRECT_LESS_THAN_MINIMUM_15_MINUTES
    val actual = validation.validateProjectFeedUrl("14", SyncIntervalTimeUnit.MINUTES)

    Assert.assertEquals(expected, actual)
  }

  @Test
  fun validIntervalShouldReturnCorrect() {
    val expected = SyncIntervalValidationResult.CORRECT
    val actual = validation.validateProjectFeedUrl("15", SyncIntervalTimeUnit.MINUTES)

    Assert.assertEquals(expected, actual)
  }
}