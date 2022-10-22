package dev.aungkyawpaing.ccdroidx.appLaunch

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.wearable.CapabilityClient
import dagger.hilt.android.AndroidEntryPoint
import dev.aungkyawpaing.ccdroidx.R
import dev.aungkyawpaing.ccdroidx.WearApp
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  private val viewModel: MainViewModel by viewModels()

  @Inject
  lateinit var capabilityClient: CapabilityClient

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    viewModel.hasPhoneAppLiveData.observe(this) { hasPhoneApp ->

      findViewById<ComposeView>(R.id.composeView).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
          WearApp {
            if (hasPhoneApp == true) {
              //TODO
            } else {
              InstallPhoneAppScreen()
            }
          }
        }
      }
    }

  }

  override fun onPause() {
    super.onPause()
    viewModel.removeCapabilityListener()
  }

  override fun onResume() {
    super.onResume()
    viewModel.addCapabilityListener()
    lifecycleScope.launch {
      viewModel.checkIfPhoneHasApp()
    }
  }


}