package dev.aungkyawpaing.ccdroidx.feature.settings.syncinterval

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import dev.aungkyawpaing.ccdroidx.R
import dev.aungkyawpaing.ccdroidx.databinding.DialogSyncIntervalInputBinding
import dev.aungkyawpaing.ccdroidx.feature.add.AddProjectViewModel

@AndroidEntryPoint
class SyncIntervalInputDialog : DialogFragment() {

  private var _binding: DialogSyncIntervalInputBinding? = null
  private val binding get() = _binding!!
  private val viewModel: AddProjectViewModel by viewModels()

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    _binding = DialogSyncIntervalInputBinding.inflate(this.layoutInflater)
    return MaterialAlertDialogBuilder(requireContext())
      .setTitle("Update Sync Interval")
      .setView(binding.root)
      .create()
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val dropDownItems = SupportedTimeUnit.values().map {
      when (it) {
        SupportedTimeUnit.MINUTES -> "Minutes"
        SupportedTimeUnit.HOUR -> "Hours"
        SupportedTimeUnit.DAY -> "Days"
      }
    }
    val adapter = ArrayAdapter(requireContext(), R.layout.item_dropdown_menu, dropDownItems)
    (binding.dropDownTimeUnit).setAdapter(adapter)

    binding.buttonSave.setOnClickListener {
      TODO()
    }

    binding.buttonCancel.setOnClickListener {
      this.dismiss()
    }

  }

  override fun onDestroyView() {
    _binding = null
    super.onDestroyView()
  }

}