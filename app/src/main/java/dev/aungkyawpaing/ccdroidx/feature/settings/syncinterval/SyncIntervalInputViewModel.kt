package dev.aungkyawpaing.ccdroidx.feature.settings.syncinterval

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aungkyawpaing.ccdroidx.coroutine.DispatcherProvider
import dev.aungkyawpaing.ccdroidx.utils.livedata.SingleLiveEvent
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SyncIntervalInputViewModel @Inject constructor(
  private val syncIntervalSettingsStore: SyncIntervalSettingsStore,
  private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

  val prefillSyncIntervalEvent = SingleLiveEvent<SyncInterval>()
  val dismissLiveEvent = SingleLiveEvent<Unit>()
  private var timeUnit: SyncIntervalTimeUnit? = null
  private var value: String? = null

  init {
    viewModelScope.launch {
      val lastSyncInterval = withContext(dispatcherProvider.io()) {
        syncIntervalSettingsStore.getSyncInterval().firstOrNull()
      } ?: return@launch

      prefillSyncIntervalEvent.postValue(lastSyncInterval)
    }
  }

  fun onSaveSyncInterval() {
    viewModelScope.launch {
      val syncInterval = SyncInterval(
        value = value!!.toInt(),
        timeUnit = timeUnit!!
      )
      withContext(dispatcherProvider.io()) {
        syncIntervalSettingsStore.setSyncInterval(syncInterval)
      }
      dismissLiveEvent.postValue(Unit)
    }
  }

  fun setTimeUnit(timeUnit: SyncIntervalTimeUnit) {
    this.timeUnit = timeUnit
  }


  fun setValue(value: String?) {
    this.value = value
  }
}