package dev.aungkyawpaing.ccdroidx._testhelper_

import dev.aungkyawpaing.ccdroidx.common.coroutine.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.ExtensionContext

@OptIn(ExperimentalCoroutinesApi::class)
private val _testDispatcher: TestDispatcher = StandardTestDispatcher()

@ExperimentalCoroutinesApi
@ExtendWith(CoroutineTestExtension::class)
abstract class CoroutineTest {
  val testDispatcher: TestDispatcher = _testDispatcher
  val scope = TestScope(testDispatcher)

  val testDispatcherProvider = object : DispatcherProvider {
    override fun default(): CoroutineDispatcher = testDispatcher
    override fun io(): CoroutineDispatcher = testDispatcher
    override fun main(): CoroutineDispatcher = testDispatcher
    override fun unconfined(): CoroutineDispatcher = testDispatcher
  }
}

@ExperimentalCoroutinesApi
class CoroutineTestExtension(val dispatcher: TestDispatcher = _testDispatcher) :
  BeforeEachCallback, AfterEachCallback {

  override fun beforeEach(context: ExtensionContext?) {
    Dispatchers.setMain(dispatcher)
  }

  override fun afterEach(context: ExtensionContext?) {
    Dispatchers.setMain(dispatcher)
  }

}
