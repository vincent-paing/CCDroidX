package dev.aungkyawpaing.ccdroidx._testhelper_

abstract class ScreenshotTest {

  fun getScreenshotPath(fileName: String): String {
    val classFullName =
      this::class.qualifiedName ?: throw IllegalStateException("Test cannot be anonymous class")
    val classDir = classFullName.substringBeforeLast(".").replace(".", "/")
    val className = classFullName.substringAfterLast(".")
    return "src/test/java/${classDir}/_screenshots/${className}_$fileName.png"
  }

}