package dev.aungkyawpaing.ccdroidx.feature.settings

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.android.material.appbar.MaterialToolbar
import dev.aungkyawpaing.ccdroidx.R

class SettingsFragment : PreferenceFragmentCompat() {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    view.findViewById<MaterialToolbar>(R.id.toolBar).setNavigationOnClickListener {
      findNavController().navigateUp()
    }
  }

  override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
    setPreferencesFromResource(R.xml.preferences, rootKey)
    findPreference<Preference?>("sync_interval")?.let { syncIntervalPref ->
      syncIntervalPref.setOnPreferenceClickListener {
        findNavController().navigate(
          SettingsFragmentDirections.actionSettingsFragmentToSyncIntervalInputDialog()
        )
        true
      }
    }
  }
}