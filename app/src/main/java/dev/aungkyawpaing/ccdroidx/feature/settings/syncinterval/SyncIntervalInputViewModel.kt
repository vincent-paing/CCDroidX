package dev.aungkyawpaing.ccdroidx.feature.settings.syncinterval

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aungkyawpaing.ccdroidx.coroutine.DispatcherProvider
import dev.aungkyawpaing.ccdroidx.utils.databinding.ObservableViewModel
import dev.aungkyawpaing.ccdroidx.utils.livedata.SingleLiveEvent
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SyncIntervalInputViewModel @Inject constructor(
  private val syncIntervalSettingsStore: SyncIntervalSettingsStore,
  private val syncIntervalValidation: SyncIntervalValidation,
  private val dispatcherProvider: DispatcherProvider
) : ObservableViewModel() {

  val validationLiveEvent = SingleLiveEvent<SyncIntervalValidationResult>()
  val prefillSyncIntervalEvent = SingleLiveEvent<SyncInterval>()
  val dismissLiveEvent = SingleLiveEvent<Unit>()

  private var timeUnit: SyncIntervalTimeUnit? = null

  val _value = ObservableField("")
  private val value get() = _value.get() ?: ""

  init {
    viewModelScope.launch {
      val lastSyncInterval = withContext(dispatcherProvider.io()) {
        syncIntervalSettingsStore.getSyncInterval().firstOrNull()
      } ?: return@launch

      _value.set(lastSyncInterval.value.toString())
      prefillSyncIntervalEvent.postValue(lastSyncInterval)
    }
  }

  fun onSaveSyncInterval() {
    viewModelScope.launch {
      val validation = syncIntervalValidation.validateProjectFeedUrl(value, timeUnit)
      validationLiveEvent.postValue(validation)

      if (validation == SyncIntervalValidationResult.CORRECT) {
        // Already checked for null in validation
        val syncInterval = SyncInterval(
          value = value.toInt(),
          timeUnit = timeUnit!!
        )
        withContext(dispatcherProvider.io()) {
          syncIntervalSettingsStore.setSyncInterval(syncInterval)
        }
        dismissLiveEvent.postValue(Unit)
      }
    }
  }

  fun setTimeUnit(timeUnit: SyncIntervalTimeUnit) {
    this.timeUnit = timeUnit
  }
}