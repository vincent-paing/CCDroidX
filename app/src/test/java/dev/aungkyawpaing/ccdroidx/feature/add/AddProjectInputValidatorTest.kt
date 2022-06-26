package dev.aungkyawpaing.ccdroidx.feature.add

import dev.aungkyawpaing.ccdroidx.feature.add.feedurlvalidation.FeedUrlValidationResult
import dev.aungkyawpaing.ccdroidx.feature.add.passwordvalidation.PasswordValidationResult
import dev.aungkyawpaing.ccdroidx.feature.add.usernamevalidation.UsernameValidationResult
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class AddProjectInputValidatorTest {

  private val validation = AddProjectInputValidator()

  @Nested
  @DisplayName("Feed url validation")
  internal inner class FeedUrl {
    @Test
    fun emptyTextReturnIncorrect() {
      val input = ""
      val expected = FeedUrlValidationResult.INCORRECT_EMPTY_TEXT
      val actual = validation.validateFeedUrl(input)

      Assertions.assertEquals(expected, actual)
    }

    @Test
    fun invalidUrlReturnIncorrect() {
      val input = "abcdefg"
      val expected = FeedUrlValidationResult.INCORRECT_INVALID_URL
      val actual = validation.validateFeedUrl(input)

      Assertions.assertEquals(expected, actual)
    }

    @Test
    fun invalidUrlReturnIncorrectTwo() {
      val input = "12345"
      val expected = FeedUrlValidationResult.INCORRECT_INVALID_URL
      val actual = validation.validateFeedUrl(input)

      Assertions.assertEquals(expected, actual)
    }

    @Test
    fun invalidUrlReturnIncorrectThree() {
      val input = "app://test"
      val expected = FeedUrlValidationResult.INCORRECT_INVALID_URL
      val actual = validation.validateFeedUrl(input)

      Assertions.assertEquals(expected, actual)
    }

    @Test
    fun validUrlReturnCorrect() {
      val input = "https://www.test.com"
      val expected = FeedUrlValidationResult.CORRECT
      val actual = validation.validateFeedUrl(input)

      Assertions.assertEquals(expected, actual)
    }

  }

  @Nested
  @DisplayName("Username validation")
  internal inner class Username {
    @Test
    fun emptyTextReturnIncorrect() {
      val input = ""
      val expected = UsernameValidationResult.INCORRECT_EMPTY_TEXT
      val actual = validation.validateUsername(input)

      Assertions.assertEquals(expected, actual)
    }


    @Test
    fun nonEmptyTextReturnCorrect() {
      val input = "Darth Vader"
      val expected = UsernameValidationResult.CORRECT
      val actual = validation.validateUsername(input)

      Assertions.assertEquals(expected, actual)
    }
  }

  @Nested
  @DisplayName("Password validation")
  internal inner class Password {
    @Test
    fun emptyTextReturnIncorrect() {
      val input = ""
      val expected = PasswordValidationResult.INCORRECT_EMPTY_TEXT
      val actual = validation.validatePassword(input)

      Assertions.assertEquals(expected, actual)
    }


    @Test
    fun nonEmptyTextReturnCorrect() {
      val input = "Ihatesand"
      val expected = PasswordValidationResult.CORRECT
      val actual = validation.validatePassword(input)

      Assertions.assertEquals(expected, actual)
    }
  }
}