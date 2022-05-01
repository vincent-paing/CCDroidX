package dev.aungkyawpaing.ccdroidx.feature.settings.syncinterval

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import dev.aungkyawpaing.ccdroidx.R
import dev.aungkyawpaing.ccdroidx.databinding.DialogSyncIntervalInputBinding
import timber.log.Timber

@AndroidEntryPoint
class SyncIntervalInputDialog : DialogFragment() {

  private var _binding: DialogSyncIntervalInputBinding? = null
  private val binding get() = _binding!!
  private val viewModel: SyncIntervalInputViewModel by viewModels()

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

    binding.textFieldTimeAmount.addTextChangedListener { editable ->
      viewModel.setValue(editable?.toString())
    }

    val dropDownItems = SyncIntervalTimeUnit.values().map {
      when (it) {
        SyncIntervalTimeUnit.MINUTES -> "Minutes"
        SyncIntervalTimeUnit.HOUR -> "Hours"
        SyncIntervalTimeUnit.DAY -> "Days"
      }
    }
    val adapter = ArrayAdapter(requireContext(), R.layout.item_dropdown_menu, dropDownItems)
    (binding.dropDownTimeUnit).setAdapter(adapter)
    binding.dropDownTimeUnit.setOnItemClickListener { parent, view, position, id ->
      setTimeUnitWithPosition(position)
    }


    binding.buttonSave.setOnClickListener {
      viewModel.onSaveSyncInterval()
    }

    binding.buttonCancel.setOnClickListener {
      this.dismiss()
    }

    viewModel.prefillSyncIntervalEvent.observe(viewLifecycleOwner) { syncInterval ->
      binding.textFieldTimeAmount.setText(syncInterval.value.toString())
      try {
        val position = when (syncInterval.timeUnit) {
          SyncIntervalTimeUnit.MINUTES -> 0
          SyncIntervalTimeUnit.HOUR -> 1
          SyncIntervalTimeUnit.DAY -> 2
        }
        setTimeUnitWithPosition(position)
        binding.dropDownTimeUnit.setText(adapter.getItem(position), false)
      } catch (exception: ArrayIndexOutOfBoundsException) {
        Timber.e(exception)
      }
    }

    viewModel.dismissLiveEvent.observe(viewLifecycleOwner) {
      this.dismiss()
    }

  }

  private fun setTimeUnitWithPosition(position: Int) {
    val timeUnit = when (position) {
      0 -> SyncIntervalTimeUnit.MINUTES
      1 -> SyncIntervalTimeUnit.HOUR
      2 -> SyncIntervalTimeUnit.DAY
      else -> throw IllegalStateException()
    }
    viewModel.setTimeUnit(timeUnit)
  }

  override fun onDestroyView() {
    _binding = null
    super.onDestroyView()
  }

}