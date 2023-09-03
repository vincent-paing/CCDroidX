package dev.aungkyawpaing.ccdroidx.feature.settings.syncinterval

import org.junit.Assert
import org.junit.Test

class SyncIntervalValidationTest {

  private val validation = SyncIntervalValidation()

  @Test
  fun emptyTextShouldReturnIncorrect() {
    val expected = SyncIntervalValidationResult.INCORRECT_EMPTY_VALUE
    val actual = validation.validateSyncInterval("")

    Assert.assertEquals(expected, actual)
  }

  @Test
  fun nonIntegerTextShouldReturnIncorrect() {
    val expected = SyncIntervalValidationResult.INCORRECT_NON_INTEGER
    val actual = validation.validateSyncInterval("abcd")

    Assert.assertEquals(expected, actual)
  }

  @Test
  fun lessThan15MinutesShouldReturnIncorrect() {
    val expected = SyncIntervalValidationResult.INCORRECT_LESS_THAN_MINIMUM_15_MINUTES
    val actual = validation.validateSyncInterval("14")

    Assert.assertEquals(expected, actual)
  }

  @Test
  fun validIntervalShouldReturnCorrect() {
    val expected = SyncIntervalValidationResult.CORRECT
    val actual = validation.validateSyncInterval("15")

    Assert.assertEquals(expected, actual)
  }
}