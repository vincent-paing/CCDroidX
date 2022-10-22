package dev.aungkyawpaing.ccdroidx.feature.settings

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint
import dev.aungkyawpaing.ccdroidx.BuildConfig
import dev.aungkyawpaing.ccdroidx.R
import dev.aungkyawpaing.ccdroidx.feature.browser.openInBrowser

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {

  companion object {
    private const val GITHUB_ISSUE_URL = "https://github.com/vincent-paing/CCDroidX/issues"
  }

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

    findPreference<Preference?>("help_feedback")?.let { pref ->
      pref.setOnPreferenceClickListener {
        openInBrowser(requireActivity(), GITHUB_ISSUE_URL)
        true
      }
    }

    findPreference<Preference?>("help_version")?.let { pref ->
      pref.summary = BuildConfig.VERSION_NAME
    }
  }
}