package dev.aungkyawpaing.ccdroidx

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class CoroutineTestRule(val dispatcher: TestDispatcher = StandardTestDispatcher()) :
  TestWatcher() {

  val scope = TestScope(dispatcher)

//  val testDispatcherProvider = object : DispatcherProvider {
//    override fun default(): CoroutineDispatcher = testDispatcher
//    override fun io(): CoroutineDispatcher = testDispatcher
//    override fun main(): CoroutineDispatcher = testDispatcher
//    override fun unconfined(): CoroutineDispatcher = testDispatcher
//  }

  override fun starting(description: Description?) {
    super.starting(description)
    Dispatchers.setMain(dispatcher)
  }

  override fun finished(description: Description?) {
    super.finished(description)
    Dispatchers.resetMain()
  }
}
