package dev.aungkyawpaing.ccdroidx.feature.add

import org.junit.Assert
import org.junit.Test

class AddProjectFeedUrlValidationTest {

  private val validation = AddProjectFeedUrlValidation()

  @Test
  fun emptyTextShouldReturnIncorrect() {
    val input = ""
    val expected = AddProjectFeedUrlValidation.ValidationResult.INCORRECT_EMPTY_TEXT
    val actual = validation.validateProjectFeedUrl(input)

    Assert.assertEquals(expected, actual)
  }

  @Test
  fun invalidUrlShouldReturnIncorrect() {
    val input = "abcdefg"
    val expected = AddProjectFeedUrlValidation.ValidationResult.INCORRECT_INVALID_URL
    val actual = validation.validateProjectFeedUrl(input)

    Assert.assertEquals(expected, actual)
  }

  @Test
  fun invalidUrlShouldReturnIncorrectTwo() {
    val input = "12345"
    val expected = AddProjectFeedUrlValidation.ValidationResult.INCORRECT_INVALID_URL
    val actual = validation.validateProjectFeedUrl(input)

    Assert.assertEquals(expected, actual)
  }

  @Test
  fun invalidUrlShouldReturnIncorrectThree() {
    val input = "app://test"
    val expected = AddProjectFeedUrlValidation.ValidationResult.INCORRECT_INVALID_URL
    val actual = validation.validateProjectFeedUrl(input)

    Assert.assertEquals(expected, actual)
  }

  @Test
  fun validUrlShouldReturnCorrect() {
    val input = "https://www.test.com"
    val expected = AddProjectFeedUrlValidation.ValidationResult.CORRECT
    val actual = validation.validateProjectFeedUrl(input)

    Assert.assertEquals(expected, actual)
  }
}