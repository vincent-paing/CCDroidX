package dev.aungkyawpaing.ccdroidx.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aungkyawpaing.ccdroidx.feature.settings.syncinterval.SyncIntervalSettingsStore
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  private val syncIntervalSettingsStore: SyncIntervalSettingsStore
) : ViewModel() {

  val syncIntervalLiveData = syncIntervalSettingsStore.getSyncInterval().asLiveData()

}