package dev.aungkyawpaing.ccdroidx.appLaunch

import androidx.lifecycle.*
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.CapabilityInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  private val capabilityClient: CapabilityClient
) : ViewModel(), CapabilityClient.OnCapabilityChangedListener {

  private val _hasPhoneAppLiveData = MutableLiveData<Boolean?>(null)
  val hasPhoneAppLiveData get() : LiveData<Boolean?> = _hasPhoneAppLiveData

  companion object {
    private const val CAPABILITY_PHONE_APP = "ccdroidx_phone_app"
  }

  suspend fun checkIfPhoneHasApp() {
    val capabilityInfo =
      capabilityClient.getCapability(CAPABILITY_PHONE_APP, CapabilityClient.FILTER_ALL).await()
    checkIfPhoneHasApp(capabilityInfo)
  }

  private fun checkIfPhoneHasApp(capabilityInfo: CapabilityInfo) {
    val hasPhoneApp = capabilityInfo.nodes.firstOrNull() != null

    _hasPhoneAppLiveData.postValue(hasPhoneApp)
  }

  fun removeCapabilityListener() {
    capabilityClient.removeListener(this, CAPABILITY_PHONE_APP)
  }

  fun addCapabilityListener() {
    capabilityClient.addListener(this, CAPABILITY_PHONE_APP)
  }

  override fun onCapabilityChanged(capabilityInfo: CapabilityInfo) {
    checkIfPhoneHasApp(capabilityInfo)
  }
}