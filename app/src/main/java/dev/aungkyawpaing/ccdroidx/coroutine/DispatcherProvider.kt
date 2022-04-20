package dev.aungkyawpaing.ccdroidx.coroutine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatcherProvider {
  fun default(): CoroutineDispatcher
  fun io(): CoroutineDispatcher
  fun main(): CoroutineDispatcher
  fun unconfined(): CoroutineDispatcher
}

class DispatcherProviderImpl : DispatcherProvider {
  override fun default(): CoroutineDispatcher = Dispatchers.Default
  override fun io(): CoroutineDispatcher = Dispatchers.IO
  override fun main(): CoroutineDispatcher = Dispatchers.Main
  override fun unconfined(): CoroutineDispatcher = Dispatchers.Unconfined
}