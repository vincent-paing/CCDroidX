package dev.aungkyawpaing.ccdroidx.feature.add

import org.junit.Assert
import org.junit.Test

class AddProjectFeedUrlValidationTest {

  private val validation = AddProjectFeedUrlValidation()

  @Test
  fun emptyTextShouldReturnIncorrect() {
    val input = ""
    val expected = FeedUrlValidationResult.INCORRECT_EMPTY_TEXT
    val actual = validation.validateProjectFeedUrl(input)

    Assert.assertEquals(expected, actual)
  }

  @Test
  fun invalidUrlShouldReturnIncorrect() {
    val input = "abcdefg"
    val expected = FeedUrlValidationResult.INCORRECT_INVALID_URL
    val actual = validation.validateProjectFeedUrl(input)

    Assert.assertEquals(expected, actual)
  }

  @Test
  fun invalidUrlShouldReturnIncorrectTwo() {
    val input = "12345"
    val expected = FeedUrlValidationResult.INCORRECT_INVALID_URL
    val actual = validation.validateProjectFeedUrl(input)

    Assert.assertEquals(expected, actual)
  }

  @Test
  fun invalidUrlShouldReturnIncorrectThree() {
    val input = "app://test"
    val expected = FeedUrlValidationResult.INCORRECT_INVALID_URL
    val actual = validation.validateProjectFeedUrl(input)

    Assert.assertEquals(expected, actual)
  }

  @Test
  fun validUrlShouldReturnCorrect() {
    val input = "https://www.test.com"
    val expected = FeedUrlValidationResult.CORRECT
    val actual = validation.validateProjectFeedUrl(input)

    Assert.assertEquals(expected, actual)
  }
}